package com.example.chatapp.ui.groupchats;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.GroubMassagesRecyclerAdapter;
import com.example.chatapp.model.MassageDetails;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.GroupActivityInterface;
import com.example.chatapp.presenters.Presenter;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupChatActivity extends AppCompatActivity implements GroupActivityInterface, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageButton)
    ImageButton sendButton;
    @BindView(R.id.editText2)
    EditText massageField;
    @BindView(R.id.groubChatRecyclerView)
    RecyclerView groubChatRecyclerView;
    @BindView(R.id.addPerson)
    ImageView addPerson;
    private String groupName;
    private Presenter presenter;
    private ArrayList<MassageDetails> massages;
    private UserInfo currentUserInfo;
    private String  userFrindsJsonString;
    private GroubMassagesRecyclerAdapter groubMassagesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        sendButton.setOnClickListener(this);
        addPerson.setOnClickListener(this);
        massages = new ArrayList<MassageDetails>();
        init();
        presenter = Presenter.getInstance();
        presenter.setGroupActivityInterface(this);
        presenter.retreiveUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getGroupMassages(groupName);
        massages.clear();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Group Chat");
        Intent intent = getIntent();
        currentUserInfo = new UserInfo();
        groubMassagesRecyclerAdapter = new GroubMassagesRecyclerAdapter(currentUserInfo, massages);
        groubChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groubChatRecyclerView.setAdapter(groubMassagesRecyclerAdapter);
        if (intent != null) {
            groupName = intent.getStringExtra("groupName");
            getSupportActionBar().setTitle(groupName);
            userFrindsJsonString = intent.getStringExtra("friends");

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButton) {
            sendButton.setEnabled(false);
            presenter.sendGroupMassage(massageField.getText().toString(), groupName, currentUserInfo);
        }if(v.getId() == R.id.addPerson){
            Intent intent = new Intent(getApplicationContext(),AddFrindsToGroub.class);
            intent.putExtra("group",groupName);
            intent.putExtra("friends",userFrindsJsonString);
            startActivity(intent);
        }
    }

    @Override
    public void onSendMassageComplete(boolean isSuccedded, String Error) {
        sendButton.setEnabled(true);
        if (isSuccedded) {
            massageField.setText("");
        } else {
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRetreiveMassage(HashMap<String, String> massage) {
        MassageDetails newMassage = ConstractMassage(massage);
        Log.i("TAG", "onRetreiveMassage: " +massage);
        boolean isNew = true;
        for (MassageDetails massageDetails : massages){
            if(massageDetails.getMassageId().equals(newMassage.getMassageId())) isNew=false;
        }
        if(isNew) massages.add(newMassage);
        groubMassagesRecyclerAdapter.setMassages(massages);
    }

    private MassageDetails ConstractMassage(HashMap<String, String> massage) {
        MassageDetails newMassage = new MassageDetails();
        newMassage.setMassageText(massage.get("massage"));
        //newMassage.setDate(massage.get("data"));
        //newMassage.setTime(massage.get("time"));
        newMassage.setMassageId(massage.get("massageId"));
        newMassage.setSenderId(massage.get("userID"));
        newMassage.setSenderPhotoUrl(massage.get("userImage"));
        return newMassage;

    }

    @Override
    public void onRetreiveUserData(int mode, String userName, String userStatus, String ImageUrl) {
        currentUserInfo.setState(userStatus);
        currentUserInfo.setUserName(userName);
        currentUserInfo.setProfileIageUri(ImageUrl);
        currentUserInfo.setUid(presenter.getcurrentUserId());
        groubMassagesRecyclerAdapter.setCurrentUserInfo(currentUserInfo);
    }
}
