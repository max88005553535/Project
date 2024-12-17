package com.example.store.controllers;

import com.example.store.services.AdminTaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTaskController {

    private final AdminTaskService admintaskService;

    public AdminTaskController(AdminTaskService admintaskService) {
        this.admintaskService = admintaskService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAllTasks(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "id") String sortBy,
                               @RequestParam(defaultValue = "true") boolean asc,
                               @RequestParam(defaultValue = "%") String title) {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        model.addAttribute("tasks", admintaskService.getAllTasks(pageable, title));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("asc", asc);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("title", title);

        return "admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        admintaskService.deleteById(id);
        return "redirect:/admin";
    }
}