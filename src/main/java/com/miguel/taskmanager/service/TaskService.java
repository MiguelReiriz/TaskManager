package com.miguel.taskmanager.service;

import com.miguel.taskmanager.entity.Task;
import com.miguel.taskmanager.entity.User;
import com.miguel.taskmanager.repository.UserRepository;
import com.miguel.taskmanager.repository.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskrepository, UserRepository userRepository) {
        this.taskRepository = taskrepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasksByUser(Long userId){
        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task updateTask(Long userId, Long taskId, Task updateTask){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("No autorizado");
        }
        task.setName(updateTask.getName());
        task.setDescription(updateTask.getDescription());
        task.setStatus(updateTask.getStatus());
        return taskRepository.save(task);
    }

    public void deleteTask(Long userId, Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("No autorizado");
        }
        taskRepository.delete(task);
    }

    public Task getTasksById(Long taskId) {
        return taskRepository.getTaskById(taskId);
    }

    public Task updateTask(Long taskId) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No autorizado");
        }
        return task;
    }
}
