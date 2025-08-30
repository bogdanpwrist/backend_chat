package com.chat.serwer.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chat.serwer.Services.MessageService;
import com.chat.serwer.Structurs.Conteiner;
import com.chat.serwer.Structurs.Massage;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/message-controller")
public class Massage_controller {
    
    @Autowired
    private MessageService messageService;

    @PostMapping("/setMassageToContainer/{id}")
    public ResponseEntity<?> getMessage(@PathVariable("id") String id_container,
                                            @RequestBody Massage massage) {
        Conteiner conteiner = new Conteiner(id_container, messageService);
        conteiner.load_Container(id_container);
        return conteiner.addMassage(massage);
    }
    
    @GetMapping("/getMassagesFromContainer/{id}")
    public ResponseEntity<?> getMassages(@PathVariable("id") String id_container) {
        Conteiner conteiner = new Conteiner(id_container, messageService);
        conteiner.load_Container(id_container);
        return ResponseEntity.ok(conteiner.getMassages());
    }
    
    @PutMapping("/markMessagesAsRead/{containerId}/{userId}")
    public ResponseEntity<?> markMessagesAsRead(@PathVariable("containerId") String containerId,
                                               @PathVariable("userId") String userId) {
        try {
            messageService.markMessagesAsRead(containerId, userId);
            return ResponseEntity.ok("Messages marked as read");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error marking messages as read: " + e.getMessage());
        }
    }
    
    @GetMapping("/getUnreadMessageCount/{containerId}/{userId}")
    public ResponseEntity<?> getUnreadMessageCount(@PathVariable("containerId") String containerId,
                                                  @PathVariable("userId") String userId) {
        try {
            Long unreadCount = messageService.getUnreadMessageCount(containerId, userId);
            return ResponseEntity.ok(unreadCount);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting unread count: " + e.getMessage());
        }
    }
}
