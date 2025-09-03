package com.chat.serwer.DBO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation_respons")
@IdClass(InvitationId.class)
public class invitation_respons_DBO {
    
    @Id
    private String username1;
    
    @Id
    private String username2;

    @Column(columnDefinition = "TEXT")
    private String eas_encrypted_key;

    // Constructors
    public invitation_respons_DBO() {}

    public invitation_respons_DBO(String username1, String username2, String eas_encrypted_key) {
        this.username1 = username1;
        this.username2 = username2;
        this.eas_encrypted_key = eas_encrypted_key;
    }

    // Getters and setters
    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public void setEas_encrypted_key(String eas_encrypted_key) {
        this.eas_encrypted_key = eas_encrypted_key;
    }

    public String getEas_encrypted_key() {
        return eas_encrypted_key;
    }
}
