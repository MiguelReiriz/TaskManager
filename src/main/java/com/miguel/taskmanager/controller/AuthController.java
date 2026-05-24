package com.miguel.taskmanager.controller;

import com.miguel.taskmanager.dto.UserRequestDTO;
import com.miguel.taskmanager.entity.User;
import com.miguel.taskmanager.service.JwtService;
import com.miguel.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRequestDTO userRequestDTO){
        String user = userService.createUser(userRequestDTO).getUsername();
        return jwtService.generateToken(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequestDTO userRequestDTO){
        return userService.login(userRequestDTO);
    }
}
