package com.example.chatapp.model;

import android.net.wifi.p2p.WifiP2pManager;

import java.security.PublicKey;

public class UserInfo {
    private String userName;
    private String userStatus;
    private String profileIageUri;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    private String Uid;
    public UserInfo(){

    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", profileIageUri='" + profileIageUri + '\'' +
                ", Uid='" + Uid + '\'' +
                '}';
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
