package com.chat.serwer.Controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chat.serwer.DBO.Invitations_DBO;
import com.chat.serwer.DBO.invitation_respons_DBO;
import com.chat.serwer.Interfaces.Invations;
import com.chat.serwer.Interfaces.Invations_response;
import com.chat.serwer.Services.ContainerEasKeyService;
import com.chat.serwer.Structurs.Invites;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class Invite_controller {
    private final Invations invations;
    private final Invations_response invations_response;
    private final ContainerEasKeyService containerEasKeyService;

    public Invite_controller(Invations invations, Invations_response invations_response, ContainerEasKeyService containerEasKeyService) {
        this.invations = invations;
        this.invations_response = invations_response;
        this.containerEasKeyService = containerEasKeyService;
    }

    @Transactional
    @PostMapping("/setInviteToContainer")
    public ResponseEntity<String> getInvite(@RequestBody Invites invite) {
        // Create a new entity instance
        Invitations_DBO invitation = new Invitations_DBO();
        invitation.setUsername1(invite.getId_sender());
        invitation.setUsername2(invite.getId_receiver());
        
        // Save it using the repository
        invations.save(invitation);
        return ResponseEntity.ok("Invite sent successfully");
    }
    @GetMapping("/getInvitesForUser/{name}")
    public ResponseEntity<?> getInvites(@PathVariable("name") String name) {
        return ResponseEntity.ok(invations.findByUsername2(name));
    }

    @GetMapping("/getInviteResponsesForUser/{name}")
    public ResponseEntity<?> getInviteResponsesForUser(@PathVariable("name") String name) {
        return ResponseEntity.ok(invations_response.findByUsername1(name));
    }

    @Transactional
    @PostMapping("/deleteInvite")
    public ResponseEntity<String> deleteInvite(@RequestBody Invites invite) {
        invations.deleteByUsername1AndUsername2(invite.getId_sender(), invite.getId_receiver());
        return ResponseEntity.ok("Invite deleted successfully");
    }

    @GetMapping("/getEasEncryptedKey")
    public ResponseEntity<String> getEasEncryptedKey(@RequestBody Invites invite) {
        String easEncryptedKey = invations_response.findEas_encrypted_keyByUsername1AndUsername2(invite.getId_sender(), invite.getId_receiver());
        return ResponseEntity.ok(easEncryptedKey);
    }

    @GetMapping("/getContainerEasKey/{containerId}/{userId}")
    public ResponseEntity<?> getContainerEasKey(@PathVariable String containerId, @PathVariable String userId) {
        try {
            var encryptedKey = containerEasKeyService.getUserEasKey(containerId, userId);
            if (encryptedKey.isPresent()) {
                return ResponseEntity.ok(Map.of(
                    "containerId", containerId,
                    "userId", userId,
                    "encryptedEasKey", encryptedKey.get()
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/setInviteResponse")
    public ResponseEntity<String> setInviteResponse(@RequestBody invitation_respons_DBO invite) {
        invitation_respons_DBO invite_Respons = new invitation_respons_DBO();
        invite_Respons.setUsername1(invite.getUsername1());
        invite_Respons.setUsername2(invite.getUsername2());
        invite_Respons.setEas_encrypted_key(invite.getEas_encrypted_key());
        invations_response.save(invite_Respons);
        invations.deleteByUsername1AndUsername2(invite.getUsername1(), invite.getUsername2());
        return ResponseEntity.ok("Invite response set successfully");
    }

    @Transactional
    @DeleteMapping("/deleteInviteResponse")
    public ResponseEntity<String> deleteInviteResponse(@RequestBody invitation_respons_DBO invite) {
        invations_response.deleteByUsername1AndUsername2(invite.getUsername1(), invite.getUsername2());
        return ResponseEntity.ok("Invite response deleted successfully");
    }

}