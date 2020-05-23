package com.example.chatapp.model;

public class MassageDetails {
    String massageText;
    String senderId;
    String type;
    String date;
    String time;

    @Override
    public String toString() {
        return "MassageDetails{" +
                "massageText='" + massageText + '\'' +
                ", senderId='" + senderId + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MassageDetails(){

    }

    public MassageDetails(String massageText, String senderId, String type) {
        this.massageText = massageText;
        this.senderId = senderId;
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMassageText() {
        return massageText;
    }

    public void setMassageText(String massageText) {
        this.massageText = massageText;
    }
}
