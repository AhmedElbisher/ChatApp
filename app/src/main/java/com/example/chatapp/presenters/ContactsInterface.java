package com.example.chatapp.presenters;

import com.example.chatapp.model.UserInfo;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public interface ContactsInterface {
    public void  OnFindContactComplete(boolean isSucceded, ArrayList<UserInfo> userInfo);

}
