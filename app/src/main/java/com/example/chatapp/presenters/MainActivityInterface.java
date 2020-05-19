package com.example.chatapp.presenters;

import com.example.chatapp.model.UserInfo;

public interface MainActivityInterface {
    public void onVerificationComplete(boolean userExsist);
    public  void  onCreatGroupComplete(boolean isSucceded);

}
