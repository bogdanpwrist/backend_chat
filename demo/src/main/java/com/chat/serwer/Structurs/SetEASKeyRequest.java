package com.chat.serwer.Structurs;

public class SetEASKeyRequest {
    private String containerId;
    private String easKey;

    SetEASKeyRequest(String containerId, String easKey) {
        this.containerId = containerId;
        this.easKey = easKey;
    }

    SetEASKeyRequest() {}

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getEasKey() {
        return easKey;
    }

    public void setEasKey(String easKey) {
        this.easKey = easKey;
    }
}
