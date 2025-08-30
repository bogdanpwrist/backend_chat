package com.chat.serwer.Interfaces;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.serwer.DBO.Invitations_DBO;

@Repository
public interface Invations extends JpaRepository<Invitations_DBO, Invitations_DBO.InvitationId> {
    // Find invitations where username2 is the receiver
    ArrayList<Invitations_DBO> findByUsername2(String username2);
    // Find invitations where username1 is the sender
    ArrayList<Invitations_DBO> findByUsername1(String username1);
    ArrayList<Invitations_DBO> findAll();
    void deleteByUsername1AndUsername2(String username1, String username2);
}