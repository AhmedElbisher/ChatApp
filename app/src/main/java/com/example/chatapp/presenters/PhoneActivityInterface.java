package com.example.chatapp.presenters;

import com.google.firebase.auth.PhoneAuthCredential;

public interface PhoneActivityInterface {
    public  void onVrerificationComplete(PhoneAuthCredential phoneAuthCredential);
    public  void onVrerificationFailed(String error);
    public  void oncodeSent();
    public  void onSignInWithPhoneCompete(boolean isSuccedded);


}
