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

    @Column(name = "Eas_key")
    private String eas_key;

    // Constructors
    public ContainerRepository() {}

    public ContainerRepository(String id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
        this.eas_key = null;
    }

    public ContainerRepository(String id, String eas_key) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
        this.eas_key = eas_key;
    }

    public ContainerRepository(String id, LocalDateTime createdAt, LocalDateTime lastActivity, String eas_key) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastActivity = lastActivity;
        this.eas_key = eas_key;
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

    public String getEas_key() {
        return eas_key;
    }

    public void setEas_key(String eas_key) {
        this.eas_key = eas_key;
    }

    // Helper method to update last activity to current time
    public void updateLastActivity() {
        this.lastActivity = LocalDateTime.now();
    }
}
