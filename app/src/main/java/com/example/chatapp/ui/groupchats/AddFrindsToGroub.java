package com.example.chatapp.ui.groupchats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.AddFriendsToGroupsRecyclerAdapter;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.AddFriendToGroupInterface;
import com.example.chatapp.presenters.Presenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFrindsToGroub extends AppCompatActivity  implements AddFriendsToGroupsRecyclerAdapter.AddFriendToChatInterfaec , AddFriendToGroupInterface {

    @BindView(R.id.addFrindsToolBar)
    Toolbar addFrindsToolBar;
    @BindView(R.id.addFriendsReclerView)
    RecyclerView addFriendsReclerView;
    private String groupName="";
    private ArrayList<UserInfo> userFriends;
    private AddFriendsToGroupsRecyclerAdapter adapter;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_frinds_to_groub);
        ButterKnife.bind(this);
        init();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void init(){
        adapter= new AddFriendsToGroupsRecyclerAdapter(this);
        addFriendsReclerView.setLayoutManager(new LinearLayoutManager(this));
        addFriendsReclerView.setAdapter(adapter);
        presenter = Presenter.getInstance();
        presenter.setAddFriendToGroupInterface(this);
        userFriends=new ArrayList<>();
        setSupportActionBar(addFrindsToolBar);
        getSupportActionBar().setTitle("Add friends to group");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent  intent = getIntent();
        if(intent != null){
            groupName= intent.getStringExtra("group");
            Gson gson = new Gson();
            List<UserInfo> temp =  Arrays.asList(gson.fromJson(intent.getStringExtra("friends"),UserInfo[].class));
            userFriends.addAll(temp);
            adapter.setUserFrinds(userFriends);
            getSupportActionBar().setTitle("Add friends to "+groupName);
        }
        presenter.retrieveGroupMembers(groupName);
    }

    @Override
    public void OnFriendAdded(UserInfo user) {
            //add it to database in group members
            //add group name to groups of friend
            // add  remove it from local friends
        presenter.addFriendToGroup(groupName,user);
        Log.i("item", "OnFriendAdded: " + user.toString());
    }

    @Override
    public void onRetreiveGroupMembers(String usersId) {
        if(userFriends != null && userFriends.size() > 0) {
            for (UserInfo temp : userFriends) {
                if (usersId.equals(temp.getUid())) {
                    userFriends.remove(temp);
                    break;
                }
            }
            adapter.setUserFrinds(userFriends);
        }
    }
}