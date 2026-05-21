package com.miguel.taskmanager.dto;

import lombok.Data;

@Data
public class TaskResponseDTO {
    Long taskID;
    String username;
    String name;
    String description;
    String status;
}
