package com.chat.serwer.Structurs;

public class User_Request {
    private String nameString;
    private String rsaPublicKeyString;

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getRsaPublicKeyString() {
        return rsaPublicKeyString;
    }

    public void setRsaPublicKeyString(String rsaPublicKeyString) {
        this.rsaPublicKeyString = rsaPublicKeyString;
    }
}
