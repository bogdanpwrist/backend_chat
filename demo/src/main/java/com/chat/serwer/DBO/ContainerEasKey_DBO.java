package com.chat.serwer.DBO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "container_eas_keys")
@IdClass(ContainerEasKey_DBO.ContainerEasKeyId.class)
public class ContainerEasKey_DBO {
    
    @Id
    @Column(name = "container_id")
    private String containerId;
    
    @Id
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "encrypted_eas_key", columnDefinition = "TEXT")
    private String encryptedEasKey;

    // Constructors
    public ContainerEasKey_DBO() {}

    public ContainerEasKey_DBO(String containerId, String userId, String encryptedEasKey) {
        this.containerId = containerId;
        this.userId = userId;
        this.encryptedEasKey = encryptedEasKey;
    }

    // Getters and setters
    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedEasKey() {
        return encryptedEasKey;
    }

    public void setEncryptedEasKey(String encryptedEasKey) {
        this.encryptedEasKey = encryptedEasKey;
    }

    // Composite key class
    public static class ContainerEasKeyId implements Serializable {
        private String containerId;
        private String userId;

        public ContainerEasKeyId() {}

        public ContainerEasKeyId(String containerId, String userId) {
            this.containerId = containerId;
            this.userId = userId;
        }

        // Getters and setters
        public String getContainerId() {
            return containerId;
        }

        public void setContainerId(String containerId) {
            this.containerId = containerId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        // equals and hashCode methods
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ContainerEasKeyId that = (ContainerEasKeyId) o;
            return containerId.equals(that.containerId) && userId.equals(that.userId);
        }

        @Override
        public int hashCode() {
            return containerId.hashCode() + userId.hashCode();
        }
    }
}
