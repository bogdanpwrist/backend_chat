package com.chat.serwer.Structurs;

public class ContainerDeletionRequest {
    private String id;

    public ContainerDeletionRequest() {}

    public ContainerDeletionRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
