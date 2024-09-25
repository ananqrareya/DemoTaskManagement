package com.ListTasks.LTasks.api;

import com.ListTasks.LTasks.Dto.TaskDto;
import com.ListTasks.LTasks.entity.Task;
import com.ListTasks.LTasks.entity.UserEntity;
import com.ListTasks.LTasks.service.TaskService;
import com.ListTasks.LTasks.service.UserService;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskApi {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;


    @GetMapping("/categories")
    public ResponseEntity<?>getAllCategory(){
        UserEntity user=userService.getAuthenticatedUser();
        List<Task>tasks=taskService.findAllCategoryByUser(user);
        System.out.println(tasks);
        return ResponseEntity.ok(tasks);
    }



    @PostMapping("/addCategory")
    public ResponseEntity<?> newTaskCategory(@RequestBody Map<String,String> request) {
        String category = request.get("category");
        if (category==null){
            throw new RuntimeException("Not add Category empty Name");
        }
        UserEntity user = userService.getAuthenticatedUser();
        try {
            taskService.addCategory(category,user);
            return ResponseEntity.ok("Add new Category successful  ");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/addtask")
    public ResponseEntity<?>addTask(@RequestBody TaskDto taskDto){
        try {
            taskService.addTask(taskDto);
            return ResponseEntity.ok("Add new Task successful");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
