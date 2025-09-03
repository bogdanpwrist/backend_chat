package com.chat.serwer.DBO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "containers")
public class ContainerRepository {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    // Constructors
    public ContainerRepository() {}

    public ContainerRepository(String id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
    }

    public ContainerRepository(String id, LocalDateTime createdAt, LocalDateTime lastActivity) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastActivity = lastActivity;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    // Helper method to update last activity to current time
    public void updateLastActivity() {
        this.lastActivity = LocalDateTime.now();
    }
}
