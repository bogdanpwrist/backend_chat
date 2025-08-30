package com.chat.serwer.DBO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "invitations")
@IdClass(Invitations_DBO.InvitationId.class)
public class Invitations_DBO {
    
    @Id
    private String username1;
    
    @Id
    private String username2;

    // Constructors
    public Invitations_DBO() {}

    public Invitations_DBO(String username1, String username2) {
        this.username1 = username1;
        this.username2 = username2;
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

    // Composite key class
    public static class InvitationId implements Serializable {
        private String username1;
        private String username2;

        public InvitationId() {}

        public InvitationId(String username1, String username2) {
            this.username1 = username1;
            this.username2 = username2;
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

        // equals and hashCode methods
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InvitationId that = (InvitationId) o;
            return username1.equals(that.username1) && username2.equals(that.username2);
        }

        @Override
        public int hashCode() {
            return username1.hashCode() + username2.hashCode();
        }
    }


}
