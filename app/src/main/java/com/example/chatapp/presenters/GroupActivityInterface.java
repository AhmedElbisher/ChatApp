package com.example.chatapp.presenters;

import java.util.ArrayList;
import java.util.HashMap;

public interface GroupActivityInterface  {
        public  void onSendMassageComplete(boolean isSuccedded, String Error);
        public  void  onRetreiveMassage(HashMap<String,String> massage);
        public  void  onRetreiveUserData(int mode , String userName,String userStatus,String ImageUrl);

}
