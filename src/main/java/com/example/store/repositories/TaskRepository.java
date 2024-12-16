package com.example.store.repositories;

import com.example.store.models.AppUser;
import com.example.store.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAppUser(AppUser appUser, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.title LIKE %:title% AND t.appUser = :user")
    Page<Task> findByAppUser(@Param("user") AppUser appUser, String title, Pageable pageable);
}