package com.miguel.taskmanager.dto;

import lombok.Data;

@Data
public class TaskRequestDTO {
    String name;
    String description;
    String status;
}
