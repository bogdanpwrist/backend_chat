package com.chat.serwer.Structurs;

public class Massage {
    private String id_sender;
    private int id;
    private String content;

    // Constructors, getters, and setters
    public Massage(String id_sender, String content, int id) {
        this.id_sender = id_sender;
        this.content = content;
        this.id = id;
    }

    public Massage() {}

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id) {
        this.id_sender = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
