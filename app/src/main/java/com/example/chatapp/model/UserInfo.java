package com.example.chatapp.model;

import android.net.wifi.p2p.WifiP2pManager;

import java.security.PublicKey;

public class UserInfo {
    private String userName;
    private String userStatus;
    private String profileIageUri;
    private String state;
    private String lastOnlinedate;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", profileIageUri='" + profileIageUri + '\'' +
                ", state='" + state + '\'' +
                ", lastOnlinedate='" + lastOnlinedate + '\'' +
                ", lastOnlinetime='" + lastOnlinetime + '\'' +
                ", Uid='" + Uid + '\'' +
                '}';
    }

    private String lastOnlinetime;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastOnlinedate() {
        return lastOnlinedate;
    }

    public void setLastOnlinedate(String lastOnlinedate) {
        this.lastOnlinedate = lastOnlinedate;
    }

    public String getLastOnlinetime() {
        return lastOnlinetime;
    }

    public void setLastOnlinetime(String lastOnlinetime) {
        this.lastOnlinetime = lastOnlinetime;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    private String Uid;
    public UserInfo(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getProfileIageUri() {
        return profileIageUri;
    }

    public void setProfileIageUri(String profileIageUri) {
        this.profileIageUri = profileIageUri;
    }
}
