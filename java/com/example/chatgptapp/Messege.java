package com.example.chatgptapp;

public class Messege {
    public static String SENT_BY_ME="me";
    public static String SENT_BY_BOT="bot";

    String messege;
    String sentBy;

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Messege(String messege, String sentBy) {
        this.messege = messege;
        this.sentBy = sentBy;
    }
}
