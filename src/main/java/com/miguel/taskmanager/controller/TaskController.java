package com.miguel.taskmanager.controller;

import com.miguel.taskmanager.entity.Task;
import com.miguel.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(@PathVariable Long userId) {
        return taskService.getTasksByUser(userId);
    }
    @GetMapping("/{taskId}")
    public Task getTasksById(@PathVariable Long taskId) {
        return taskService.getTasksById(taskId);
    }

    @PostMapping
    public Task createTask(@PathVariable Long userId, @RequestBody Task task){
        return taskService.createTask(userId, task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(
            @PathVariable Long userId,
            @PathVariable Long taskId) {

        taskService.deleteTask(userId, taskId);
    }
    @PutMapping("/{taskId}")
    public Task updateTask(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestBody Task task) {
        return taskService.updateTask(taskId, userId, task);

    }

}
