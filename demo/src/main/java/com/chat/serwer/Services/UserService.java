package com.chat.serwer.Services;

import org.springframework.stereotype.Service;

import com.chat.serwer.DBO.User_DBO;
import com.chat.serwer.Interfaces.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user
    public User_DBO createUser(String name) {
        User_DBO user = new User_DBO();
        user.setName(name);
        return userRepository.save(user); // INSERT into DB
    }

    // Get a user by ID
    public Optional<User_DBO> getUserById(Long id) {
        return userRepository.findById(id); // SELECT from DB
    }

    // Get all users
    public List<User_DBO> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

