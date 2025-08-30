package com.chat.serwer.Interfaces;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.serwer.DBO.invitation_respons_DBO;

@Repository
public interface Invations_response extends JpaRepository<invitation_respons_DBO, invitation_respons_DBO.InvitationId> {
    // Find invitation responses where username2 is the receiver
    ArrayList<invitation_respons_DBO> findByUsername2(String username2);
    // Find invitation responses where username1 is the sender
    ArrayList<invitation_respons_DBO> findByUsername1(String username1);
    ArrayList<invitation_respons_DBO> findAll();
    void deleteByUsername1AndUsername2(String username1, String username2);
    String findEas_encrypted_keyByUsername1AndUsername2(String username1, String username2);
}