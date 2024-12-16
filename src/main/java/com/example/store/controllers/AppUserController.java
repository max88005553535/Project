package com.example.store.controllers;

import com.example.store.repositories.AppUserRepository;

import com.example.store.repositories.TaskRepository;
import com.example.store.services.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppUserController {
    private final AppUserService appUserService;
    AppUserRepository appUserRepository;
    private final TaskRepository tasksRepository;
    public AppUserController(final AppUserService appUserService, final AppUserRepository appUserRepository, final TaskRepository tasksRepository) {
        this.appUserService = appUserService;
        this.tasksRepository = tasksRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        appUserService.registerAppUser(username, password);
        return "redirect:/login";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }

}