package com.chat.serwer.Structurs;

public class ContainerCreationRequest {
    private String id;
    private String name;
    private String eas_key;

    // Default constructor for JSON deserialization
    public ContainerCreationRequest() {}

    public ContainerCreationRequest(String id, String name, String eas_key) {
        this.id = id;
        this.name = name;
        this.eas_key = eas_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEasKey() {
        return eas_key;
    }

    public void setEasKey(String eas_key) {
        this.eas_key = eas_key;
    }

    @Override
    public String toString() {
        return "Konteiner_creation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", eas_key='" + eas_key + '\'' +
                '}';
    }
}
