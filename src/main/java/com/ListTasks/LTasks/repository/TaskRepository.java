package com.ListTasks.LTasks.repository;

import com.ListTasks.LTasks.entity.Task;
import com.ListTasks.LTasks.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task>findByUser(UserEntity user);
    Optional<Task>findByCategoryTask(String categoryName);
}
