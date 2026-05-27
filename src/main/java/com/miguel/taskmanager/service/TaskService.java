package com.miguel.taskmanager.service;

import com.miguel.taskmanager.dto.TaskRequestDTO;
import com.miguel.taskmanager.dto.TaskResponseDTO;
import com.miguel.taskmanager.entity.Task;
import com.miguel.taskmanager.entity.User;
import com.miguel.taskmanager.repository.UserRepository;
import com.miguel.taskmanager.repository.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskrepository, UserRepository userRepository) {
        this.taskRepository = taskrepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getTasksByUser(Long userId){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getUsername().equals(currentUser)){
            throw new RuntimeException("Incorrect User");
        }

        List<TaskResponseDTO> taskResponseList= new ArrayList<>();
        List<Task> filteredTasks = taskRepository.findByUserId(userId);
        for (Task task : filteredTasks){
            TaskResponseDTO dto = new TaskResponseDTO();
            dto.setName(task.getName());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setTaskID(task.getId());
            dto.setUsername(task.getUser().getUsername());
            taskResponseList.add(dto);
       }
        
        return taskResponseList;
    }

    public TaskResponseDTO createTask(Long userId, TaskRequestDTO taskRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Task task = new Task();
        task.setName(taskRequestDTO.getName());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskRequestDTO.getStatus());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        TaskResponseDTO savedTaskResponse = new TaskResponseDTO();
        savedTaskResponse.setName(savedTask.getName());
        savedTaskResponse.setDescription(savedTask.getDescription());
        savedTaskResponse.setStatus(savedTask.getStatus());
        savedTaskResponse.setTaskID(savedTask.getId());
        savedTaskResponse.setUsername(user.getUsername());
        return savedTaskResponse;
    }

    public TaskResponseDTO updateTask(Long userId, Long taskId, TaskRequestDTO updateTask){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("No autorizado");
        }
        TaskResponseDTO response = new TaskResponseDTO();
        response.setName(updateTask.getName());
        response.setDescription(updateTask.getDescription());
        response.setStatus(updateTask.getStatus());           
        response.setTaskID(task.getId());
        response.setUsername(task.getUser().getUsername());

        task.setName(updateTask.getName());
        task.setDescription(updateTask.getDescription());
        task.setStatus(updateTask.getStatus());
        taskRepository.save(task);     
        
        return response;
    }

    public void deleteTask(Long userId, Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("No autorizado");
        }
        taskRepository.delete(task);
    }

    public TaskResponseDTO getTasksById(Long taskId) {
        Task savedTask = taskRepository.getTaskById(taskId);
        TaskResponseDTO response = new TaskResponseDTO();
        response.setName(savedTask.getName());
        response.setDescription(savedTask.getDescription());
        response.setStatus(savedTask.getStatus());
        response.setTaskID(savedTask.getId());
        response.setUsername(savedTask.getUser().getUsername());

        return response;
    }
}
