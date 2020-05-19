package com.example.chatapp.presenters;

public interface FrindProfileInterface {
    public void onSendMassageReqeustCompleted(boolean isSucceded,String error);
    public void onCancelMasssagrReqeustComplete(boolean isSucceded,String error);
    public  void  onAcceptReqeustCompleted(boolean isSucceded);
    public  void  onRemoveFriendCompleted(boolean isSucceded);
}
