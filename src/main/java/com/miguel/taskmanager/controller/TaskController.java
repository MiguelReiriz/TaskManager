package com.miguel.taskmanager.controller;

import com.miguel.taskmanager.dto.TaskRequestDTO;
import com.miguel.taskmanager.dto.TaskResponseDTO;
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
    public List<TaskResponseDTO> getTasks(@PathVariable Long userId) {
        return taskService.getTasksByUser(userId);
    }

    @GetMapping("/{taskId}")
    public TaskResponseDTO getTasksById(@PathVariable Long taskId) {
        return taskService.getTasksById(taskId);
    }

    @PostMapping
    public TaskResponseDTO createTask(@PathVariable Long userId, @RequestBody TaskRequestDTO taskRequestDTO){
        return taskService.createTask(userId, taskRequestDTO);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(
            @PathVariable Long userId,
            @PathVariable Long taskId) {

        taskService.deleteTask(userId, taskId);
    }
    @PutMapping("/{taskId}")
    public TaskResponseDTO updateTask(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestBody TaskRequestDTO request) {
        return taskService.updateTask(taskId, userId, request);

    }

}
