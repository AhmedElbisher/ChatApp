package com.example.chatapp.presenters;

public interface SittingInterface {
    public  void onUpdateInfoComplete(boolean updateDone , String Error);
    public  void  onRetreiveUserData(int mode , String userName,String userStatus,String ImageUrl);
    public  void OnUploadImageSucceded(boolean isSucceded);
    public void  OnSaveImageTOUserDAtaSucceded(boolean isSucceded);
}
