package com.example.store.services;

import com.example.store.models.AppUser;
import com.example.store.models.Categories;
import com.example.store.models.Task;
import com.example.store.repositories.AppUserRepository;
import com.example.store.repositories.CategoriesRepository;
import com.example.store.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository tasksRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    CategoriesRepository categoriesRepository;
    @Value("${upload.dir}")
    private String uploadDir;

    public Page<Task> getTasksByUser(String username, String title, Pageable pageable) {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return tasksRepository.findByAppUser(user, title, pageable);
    }

    public Task addTask(String username, Task task, Long categoryId) throws IOException {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        task.setAppUser(user);
        if (categoryId != null) {
            Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }
        return tasksRepository.save(task);
    }

    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }

}