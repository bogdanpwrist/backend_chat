package com.chat.serwer.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.serwer.DBO.Message_DBO;
import com.chat.serwer.DBO.UserContainerRepository;
import com.chat.serwer.Interfaces.MessageRepositoryInterface;
import com.chat.serwer.Interfaces.UserContainerRepositoryInterface;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepositoryInterface messageRepository;
    
    @Autowired
    private UserContainerRepositoryInterface userContainerRepository;
    
    public Message_DBO saveMessage(String containerId, String senderId, String content) {
        Message_DBO message = new Message_DBO(containerId, senderId, content);
        return messageRepository.save(message);
    }
    
    public List<Message_DBO> getMessagesByContainer(String containerId) {
        return messageRepository.findByContainerIdOrderBySentAt(containerId);
    }
    
    public void markMessagesAsRead(String containerId, String userId) {
        // Determine user position in container (user1 or user2)
        List<UserContainerRepository> usersInContainer = userContainerRepository.findByContainerId(containerId);
        
        if (usersInContainer.size() >= 2) {
            String user1 = usersInContainer.get(0).getUserId();
            String user2 = usersInContainer.get(1).getUserId();
            
            if (userId.equals(user1)) {
                messageRepository.markAsReadByUser1(containerId, userId);
            } else if (userId.equals(user2)) {
                messageRepository.markAsReadByUser2(containerId, userId);
            }
        }
    }
    
    public Long getUnreadMessageCount(String containerId, String userId) {
        // Determine user position in container
        List<UserContainerRepository> usersInContainer = userContainerRepository.findByContainerId(containerId);
        
        if (usersInContainer.size() >= 2) {
            String user1 = usersInContainer.get(0).getUserId();
            String user2 = usersInContainer.get(1).getUserId();
            
            if (userId.equals(user1)) {
                return messageRepository.countUnreadMessages(containerId, userId, 1);
            } else if (userId.equals(user2)) {
                return messageRepository.countUnreadMessages(containerId, userId, 2);
            }
        }
        
        return 0L;
    }
    
    public void deleteMessagesByContainer(String containerId) {
        messageRepository.deleteByContainerId(containerId);
    }

}
