package com.example.chatapp.presenters;

import com.example.chatapp.model.UserInfo;

import java.util.ArrayList;

public interface FindRequestsInterface {
    public void  OnFindReqeustsComplete(boolean isSucceded, ArrayList<UserInfo> userInfo);

}
