package com.chat.serwer.Structurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.chat.serwer.DBO.Message_DBO;
import com.chat.serwer.Services.MessageService;

public class Conteiner {
    private List<Massage> massages;
    private String containerId;
    private MessageService messageService;

    public Conteiner() {
        this.massages = new ArrayList<>();
    }
    
    public Conteiner(String containerId) {
        this.massages = new ArrayList<>();
        this.containerId = containerId;
    }
    
    public Conteiner(String containerId, MessageService messageService) {
        this.massages = new ArrayList<>();
        this.containerId = containerId;
        this.messageService = messageService;
    }

    public List<Massage> getMassages() {
        return massages;
    }

    public String getContainerId() {
        return containerId;
    }
    
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setMassages(List<Massage> massages) {
        this.massages = massages;
    }

    public ResponseEntity<?> addMassage(Massage massage) {
        try {
            if (messageService != null) {
                // Save to database
                messageService.saveMessage(containerId, massage.getId_sender(), massage.getContent());
            }
            
            // Add to local list for compatibility
            this.massages.add(massage);
            
            return ResponseEntity.ok("Massage added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving message: " + e.getMessage());
        }
    }

    public ResponseEntity<?> removeMassage(Massage massage) {
        this.massages.remove(massage);
        return ResponseEntity.ok("Massage removed successfully");
    }

    public ResponseEntity<?> load_Container(String containerId) {
        try {
            this.containerId = containerId;
            this.massages.clear();
            
            if (messageService != null) {
                // Load messages from database
                List<Message_DBO> dbMessages = messageService.getMessagesByContainer(containerId);
                
                // Convert to Massage objects for compatibility
                for (Message_DBO dbMessage : dbMessages) {
                    Massage massage = new Massage(
                        dbMessage.getSenderId(), 
                        dbMessage.getContent(), 
                        dbMessage.getId().intValue()
                    );
                    this.massages.add(massage);
                    System.out.println(dbMessage.getSenderId() + ": " + dbMessage.getContent() + ": " + dbMessage.getId());
                }
            }
            
            return ResponseEntity.ok("Container loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error loading container: " + e.getMessage());
        }
    }
    
    public ResponseEntity<?> markMessagesAsRead(String userId) {
        try {
            if (messageService != null) {
                messageService.markMessagesAsRead(containerId, userId);
            }
            return ResponseEntity.ok("Messages marked as read");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error marking messages as read: " + e.getMessage());
        }
    }
    
    public Long getUnreadMessageCount(String userId) {
        try {
            if (messageService != null) {
                return messageService.getUnreadMessageCount(containerId, userId);
            }
            return 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    // Deprecated methods for backward compatibility
    @Deprecated
    public void save_Container() {
        // This method is deprecated - messages are now saved directly to database
    }

    @Deprecated
    public void update_Container() {
        // This method is deprecated - messages are now saved directly to database
    }
}
