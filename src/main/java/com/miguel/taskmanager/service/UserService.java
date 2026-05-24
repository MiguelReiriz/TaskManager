package com.miguel.taskmanager.service;

import com.miguel.taskmanager.dto.TaskResponseDTO;
import com.miguel.taskmanager.dto.UserRequestDTO;
import com.miguel.taskmanager.dto.UserResponseDTO;
import com.miguel.taskmanager.entity.Task;
import com.miguel.taskmanager.entity.User;
import com.miguel.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public UserService(UserRepository repository, PasswordEncoder encoder, JwtService jwt) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> userResponseList = new ArrayList<>();
        List<User> filteredUsers = repository.findAll();
        for (User user : filteredUsers) {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setUserID(user.getId());
            dto.setUsername(user.getUsername());
            userResponseList.add(dto);
        }
        return userResponseList;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(encoder.encode(userRequestDTO.getPassword()));
        User savedUser = repository.save(user);
        UserResponseDTO savedUserResponse = new UserResponseDTO();
        savedUserResponse.setUserID(savedUser.getId());
        savedUserResponse.setUsername(savedUser.getUsername());
        return savedUserResponse;
    }

    public UserResponseDTO getUserById(Long userId) {
        User savedUser = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDTO response = new UserResponseDTO();
        response.setUsername(savedUser.getUsername());
        response.setUserID(savedUser.getId());

        return response;
    }

    public User getUserByName(String userName) {
        return repository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO updateUser) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO response = new UserResponseDTO();
        response.setUsername(updateUser.getUsername());
        response.setUserID(user.getId());

        user.setUsername(updateUser.getUsername());
        repository.save(user);

        return response;
    }

    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.delete(user);
    }

    public String login(UserRequestDTO request) {
        String username = request.getUsername();
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String passwordEncripted = user.getPassword();
        String passwordRaw = request.getPassword();
        if (encoder.matches(passwordRaw, passwordEncripted)) {
           return jwt.generateToken(username);
        } else {
            throw new RuntimeException("Incorrect password");
        }

    }

}