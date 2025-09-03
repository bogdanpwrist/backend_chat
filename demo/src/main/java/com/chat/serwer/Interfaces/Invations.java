package com.chat.serwer.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.serwer.DBO.Invitations_DBO;
import com.chat.serwer.DBO.InvitationId;

@Repository
public interface Invations extends JpaRepository<Invitations_DBO, InvitationId> {
    // Find invitations where username2 is the receiver
    java.util.List<Invitations_DBO> findByUsername2(String username2);
    // Find invitations where username1 is the sender
    java.util.List<Invitations_DBO> findByUsername1(String username1);
    void deleteByUsername1AndUsername2(String username1, String username2);
}