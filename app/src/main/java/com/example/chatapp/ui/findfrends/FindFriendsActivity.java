package com.example.chatapp.ui.findfrends;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.UsersListAdapter;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.FindFrindsInterface;
import com.example.chatapp.presenters.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FindFriendsActivity extends AppCompatActivity implements FindFrindsInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.usersRecyclerView)
    RecyclerView usersRecyclerView;
    UsersListAdapter usersListAdapter;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Frinds");
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersListAdapter = new UsersListAdapter();
        usersRecyclerView.setAdapter(usersListAdapter);
        presenter= Presenter.getInstance();
        presenter.setFindFrindsInterface(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        usersListAdapter.clearDate();
        presenter.retreiveUsers();

    }

    @Override
    public void onRetreiveUser(UserInfo currentUser) {
            usersListAdapter.setUsers(currentUser);
    }
}
