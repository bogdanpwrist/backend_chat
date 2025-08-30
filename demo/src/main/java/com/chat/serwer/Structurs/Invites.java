package com.chat.serwer.Structurs;

public class Invites {
    private String id_sender;
    private String id_receiver;

    public Invites() {}

    public Invites(String id_sender, String id_receiver) {
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
    }

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id_sender) {
        this.id_sender = id_sender;
    }

    public String getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(String id_receiver) {
        this.id_receiver = id_receiver;
    }
}

