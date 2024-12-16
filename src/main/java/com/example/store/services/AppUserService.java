package com.example.store.services;

import com.example.store.models.AppUser;
import com.example.store.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAllAppUsers(){
        return appUserRepository.findAll();
    }

    public AppUser saveAppUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    public AppUser registerAppUser(String username, String password){
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRole(AppUser.Role.USER);
        return saveAppUser(appUser);
    }

    public Optional<AppUser> getAppUserByUsername(String username){
        return appUserRepository.findByUsername(username);
    }
}