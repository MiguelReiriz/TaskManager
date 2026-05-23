package com.miguel.taskmanager.controller;

import com.miguel.taskmanager.dto.UserRequestDTO;
import com.miguel.taskmanager.dto.UserResponseDTO;
import com.miguel.taskmanager.entity.User;
import com.miguel.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    public UserResponseDTO createTask(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

}
