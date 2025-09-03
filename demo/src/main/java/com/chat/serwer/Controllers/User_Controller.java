package com.chat.serwer.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chat.serwer.DBO.User_DBO;
import com.chat.serwer.Interfaces.UserRepository;
import com.chat.serwer.Structurs.User_Request;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/users")
public class User_Controller {
    private final UserRepository userRepository;

    public User_Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User_Request userRequest) {
        String user_NAMEString = userRequest.getNameString();
        String user_RSAString = userRequest.getRsaPublicKeyString();

        if (user_NAMEString == null || user_NAMEString.trim().isEmpty()|| userRepository.existsByName(user_NAMEString)) {
            return ResponseEntity.status(500).body("Error invalid user name.");
        }

        User_DBO user = new User_DBO();
        user.setName(user_NAMEString);
        user.setRsaPublicKey(user_RSAString);
        userRepository.save(user);
        return ResponseEntity.ok("User added successfully");
    }

    @Transactional
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody String user_NAMEString) {
        if (user_NAMEString == null || user_NAMEString.trim().isEmpty()|| !userRepository.existsByName(user_NAMEString)) {
            return ResponseEntity.status(500).body("Error invalid user name.");
        }

        userRepository.deleteByName(user_NAMEString);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<User_DBO>> getAllUsers() {
        List<User_DBO> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Transactional
    @GetMapping("/getByName/{name}")
    public ResponseEntity<User_DBO> getUserByName(@PathVariable String name) {
        User_DBO user = userRepository.findByName(name);
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> userExists(@PathVariable String name) {
        boolean exists = userRepository.existsByName(name);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/getId/{name}")
    public ResponseEntity<Long> getUserIdByName(@PathVariable String name) {
        Long userId = userRepository.findIdByName(name);
        if (userId == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<User_DBO> getUserById(@PathVariable Long id) {
        Optional<User_DBO> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(user.get());
    }

}
