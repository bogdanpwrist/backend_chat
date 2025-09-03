package com.chat.serwer.Structurs;

public class SetUserEasKeyRequest {
    private String containerId;
    private String userId;
    private String encryptedEasKey;

    public SetUserEasKeyRequest() {}

    public SetUserEasKeyRequest(String containerId, String userId, String encryptedEasKey) {
        this.containerId = containerId;
        this.userId = userId;
        this.encryptedEasKey = encryptedEasKey;
    }

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
}
