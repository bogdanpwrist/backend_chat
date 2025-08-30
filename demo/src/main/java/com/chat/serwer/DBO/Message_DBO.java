package com.chat.serwer.DBO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message_DBO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "container_id", nullable = false)
    private String containerId;
    
    @Column(name = "sender_id", nullable = false)
    private String senderId;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    
    @Column(name = "read_by_user1", nullable = false)
    private Boolean readByUser1 = false;
    
    @Column(name = "read_by_user2", nullable = false)
    private Boolean readByUser2 = false;
    
    // Constructors
    public Message_DBO() {}
    
    public Message_DBO(String containerId, String senderId, String content) {
        this.containerId = containerId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = LocalDateTime.now();
        this.readByUser1 = false;
        this.readByUser2 = false;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContainerId() {
        return containerId;
    }
    
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    
    public String getSenderId() {
        return senderId;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public Boolean getReadByUser1() {
        return readByUser1;
    }
    
    public void setReadByUser1(Boolean readByUser1) {
        this.readByUser1 = readByUser1;
    }
    
    public Boolean getReadByUser2() {
        return readByUser2;
    }
    
    public void setReadByUser2(Boolean readByUser2) {
        this.readByUser2 = readByUser2;
    }
}
