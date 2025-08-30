package com.chat.serwer.DBO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_containers", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "container_id"}))
public class UserContainerRepository {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "container_id", nullable = false)
    private String containerId;
    
    @Column(name = "joined_at")
    private LocalDateTime joinedAt;
    
    // Constructors
    public UserContainerRepository() {}
    
    public UserContainerRepository(String userId, String containerId) {
        this.userId = userId;
        this.containerId = containerId;
        this.joinedAt = LocalDateTime.now();
    }
    
    public UserContainerRepository(String userId, String containerId, LocalDateTime joinedAt) {
        this.userId = userId;
        this.containerId = containerId;
        this.joinedAt = joinedAt;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getContainerId() {
        return containerId;
    }
    
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
    
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
