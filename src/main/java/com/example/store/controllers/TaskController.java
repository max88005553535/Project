package com.example.store.controllers;

import com.example.store.models.AppUser;
import com.example.store.models.Task;
import com.example.store.repositories.AppUserRepository;
import com.example.store.repositories.CategoriesRepository;
import com.example.store.repositories.TaskRepository;
import com.example.store.services.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final AppUserRepository appUserRepository;
    private final TaskRepository tasksRepository;
    private final CategoriesRepository categoriesRepository;

    public TaskController(TaskService taskService, AppUserRepository appUserRepository, TaskRepository tasksRepository, CategoriesRepository categoriesRepository) {
        this.taskService = taskService;
        this.appUserRepository = appUserRepository;
        this.tasksRepository = tasksRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping
    public String getTasks(@AuthenticationPrincipal UserDetails userDetails, Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(defaultValue = "id") String sortBy,
                           @RequestParam(defaultValue = "true") boolean asc,
                           @RequestParam(defaultValue = "%") String title) {
        String username = userDetails.getUsername();
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        model.addAttribute("tasks", taskService.getTasksByUser(username, title, pageable));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("asc", asc);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("title", title);

        return "tasks";
    }

    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("categories", taskService.getCategories());
        model.addAttribute("users", appUserRepository.findAll());
        return "add_task";
    }

    @PostMapping("/add")
    public String addTask(@AuthenticationPrincipal UserDetails userDetails,
                          @ModelAttribute Task task,
                          @RequestParam Long userId,
                          @RequestParam(required = false) Long categoryId) throws IOException {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        task.setAppUser(user);
        taskService.addTask(user.getUsername(), task, categoryId);
        return "redirect:/home";
    }


}