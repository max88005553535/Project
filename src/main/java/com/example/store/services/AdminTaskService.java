package com.example.store.services;

import com.example.store.models.Task;
import com.example.store.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AdminTaskService {
    @Autowired
    TaskRepository tasksRepository;

    public void deleteById(Long id) {
        tasksRepository.deleteById(id);
    }

    public AdminTaskService(TaskRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public Page<Task> getAllTasks(Pageable pageable, String title) {
        return tasksRepository.findAll(pageable);
    }
}