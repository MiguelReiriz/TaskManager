package com.miguel.taskmanager.service;

import com.miguel.taskmanager.dto.UserRequestDTO;
import com.miguel.taskmanager.dto.UserResponseDTO;
import com.miguel.taskmanager.entity.User;
import com.miguel.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        User savedUser = repository.save(user);
        UserResponseDTO savedUserResponse = new UserResponseDTO();
        savedUserResponse.setUserID(savedUser.getId());
        savedUserResponse.setUsername(savedUser.getUsername());
        return savedUserResponse;
    }
}
