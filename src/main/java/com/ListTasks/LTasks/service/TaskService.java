package com.ListTasks.LTasks.service;

import com.ListTasks.LTasks.Dto.TaskDto;
import com.ListTasks.LTasks.entity.SubTask;
import com.ListTasks.LTasks.entity.Task;
import com.ListTasks.LTasks.entity.UserEntity;
import com.ListTasks.LTasks.repository.SubTaskRepository;
import com.ListTasks.LTasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
@Autowired
    private TaskRepository taskRepository;
@Autowired
    private SubTaskRepository subTaskRepository;


    public void addCategory(String category, UserEntity user) {
        Task task=new Task();
        task.setCategoryTask(category);
        task.setUser(user);
        taskRepository.save(task);
    }

    public List<Task> findAllCategoryByUser(UserEntity user) {
    return taskRepository.findByUser(user);
    }

    public void addTask(TaskDto taskDto) {
        Task task=taskRepository.findByCategoryTask(taskDto.getCategoryName()).orElseThrow(()->new RuntimeException("Not Found Category"));
        SubTask subTask=new SubTask();
        subTask.setTask(taskDto.getTask());
        subTask.setCategory(task);
        subTaskRepository.save(subTask);
    }
}
