package com.example.chatapp.presenters;

import com.example.chatapp.model.MassageDetails;

import java.util.ArrayList;

public interface SingleChatInterface {

    public  void  onSendMassagSucessed();
    public void onSendMassagFailed(String Erro);
    public void onRetrieveMassagesComplete(boolean succeded, ArrayList<MassageDetails> massages);
}
