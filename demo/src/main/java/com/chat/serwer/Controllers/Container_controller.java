package com.chat.serwer.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chat.serwer.Structurs.ContainerCreationRequest;
import com.chat.serwer.Structurs.ContainerDeletionRequest;
import com.chat.serwer.Structurs.SetUserEasKeyRequest;
import com.chat.serwer.DBO.ContainerRepository;
import com.chat.serwer.DBO.UserContainerRepository;
import com.chat.serwer.DBO.ContainerEasKey_DBO;
import com.chat.serwer.Interfaces.ContainerRepositoryInterface;
import com.chat.serwer.Interfaces.UserContainerRepositoryInterface;
import com.chat.serwer.Interfaces.ContainerEasKeyRepository;
import com.chat.serwer.Services.MessageService;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class Container_controller {
    
    @Autowired
    private ContainerRepositoryInterface containerRepository;
    
    @Autowired
    private UserContainerRepositoryInterface userContainerRepository;
    
    @Autowired
    private ContainerEasKeyRepository containerEasKeyRepository;
    
    @Autowired
    private MessageService messageService;

    @Transactional
    @PostMapping("/createContainer")
    public ResponseEntity<?> createContainer(@RequestBody ContainerCreationRequest request) {
        try {
            // Parse users from the composite ID (user1:user2)
            String[] users = request.getId().split(":");
            if (users.length != 2) {
                return ResponseEntity.badRequest().body("Invalid format");
            }
            
            // Generate unique container ID
            String containerId = "chat_" + UUID.randomUUID().toString();

            // Create container without AES key (it will be set separately for each user)
            ContainerRepository container = new ContainerRepository(containerId);
            containerRepository.save(container);
            
            // Add both users to the container
            userContainerRepository.save(new UserContainerRepository(users[0], containerId));
            userContainerRepository.save(new UserContainerRepository(users[1], containerId));
            
            return ResponseEntity.ok(Map.of(
                "containerId", containerId,
                "users", Arrays.asList(users)
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getContainersByUser/{userId}")
    public ResponseEntity<?> getContainersByUser(@PathVariable String userId) {
        try {
            // Get all containers where this user is a member
            List<UserContainerRepository> userContainers = userContainerRepository.findByUserId(userId);
            
            List<Map<String, Object>> result = new ArrayList<>();
            
            for (UserContainerRepository uc : userContainers) {
                // Get other users in this container
                List<UserContainerRepository> allUsersInContainer = 
                    userContainerRepository.findByContainerId(uc.getContainerId());
                
                // Find the other user (not the current user)
                String otherUser = allUsersInContainer.stream()
                    .map(UserContainerRepository::getUserId)
                    .filter(user -> !user.equals(userId))
                    .findFirst()
                    .orElse("Unknown");
                
                result.add(Map.of(
                    "id", uc.getContainerId(),           // Real container ID
                    "user", userId,                      // Current user
                    "konteiner", otherUser              // Other user (for compatibility)
                ));
            }
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/deleteContainer") 
    public ResponseEntity<?> deleteContainer(@RequestBody ContainerDeletionRequest request) {
        try {
            String[] users = request.getId().split(":");
            if (users.length != 2) {
                return ResponseEntity.badRequest().body("Invalid format");
            }
            
            // Find container where both users are members
            List<UserContainerRepository> user1Containers = userContainerRepository.findByUserId(users[0]);
            List<UserContainerRepository> user2Containers = userContainerRepository.findByUserId(users[1]);
            
            // Find common container
            String sharedContainerId = user1Containers.stream()
                .filter(uc1 -> user2Containers.stream()
                    .anyMatch(uc2 -> uc2.getContainerId().equals(uc1.getContainerId())))
                .map(UserContainerRepository::getContainerId)
                .findFirst()
                .orElse(null);
            
            if (sharedContainerId != null) {
                // Delete messages first
                messageService.deleteMessagesByContainer(sharedContainerId);
                // Delete EAS keys for all users
                containerEasKeyRepository.deleteByContainerId(sharedContainerId);
                // Delete user-container relationships
                userContainerRepository.deleteByContainerId(sharedContainerId);
                // Delete container
                containerRepository.deleteById(sharedContainerId);
                return ResponseEntity.ok("Container deleted");
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/setUserEasKey")
    public ResponseEntity<?> setUserEasKey(@RequestBody SetUserEasKeyRequest request) {
        try {
            String containerId = request.getContainerId();
            String userId = request.getUserId();
            String encryptedEasKey = request.getEncryptedEasKey();

            // Validate request
            if (containerId == null || userId == null || encryptedEasKey == null) {
                return ResponseEntity.badRequest().body("Invalid request - all fields required");
            }

            // Save or update the encrypted EAS key for this user and container
            ContainerEasKey_DBO easKeyRecord = new ContainerEasKey_DBO(containerId, userId, encryptedEasKey);
            containerEasKeyRepository.save(easKeyRecord);
            
            return ResponseEntity.ok("User EAS key set successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getUserEasKey/{containerId}/{userId}")
    public ResponseEntity<?> getUserEasKey(@PathVariable String containerId, @PathVariable String userId) {
        try {
            // Validate parameters
            if (containerId == null || userId == null) {
                return ResponseEntity.badRequest().body("Invalid parameters");
            }

            // Find the encrypted EAS key for this user and container
            var easKeyRecord = containerEasKeyRepository.findByContainerIdAndUserId(containerId, userId);
            
            if (easKeyRecord.isPresent()) {
                return ResponseEntity.ok(Map.of(
                    "containerId", containerId,
                    "userId", userId,
                    "encryptedEasKey", easKeyRecord.get().getEncryptedEasKey()
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}