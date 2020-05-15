package com.example.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.GroupsRecycleAdapter;
import com.example.chatapp.presenters.GroupFragmentInterface;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.ui.groupchats.GroupChatActivity;

import java.util.ArrayList;

import butterknife.BindView;


public class GroupFragment extends Fragment implements GroupFragmentInterface,GroupsRecycleAdapter.GroupClickedInterface {


    RecyclerView.LayoutManager layoutManager;
    GroupsRecycleAdapter adapter;
    Presenter presenter;
    RecyclerView recyclerView;

    public GroupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        initRecylerView(view);
        presenter = Presenter.getInstance();
        presenter.setGroupFragmentInterface(this);
        presenter.retreiveGroupsNames();
        return view;
    }

    private void initRecylerView(View v) {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView =v.findViewById(R.id.grouprecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new GroupsRecycleAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onGroupsArrayChange(ArrayList<String> newGroupArray) {
        adapter.setGroupArray(newGroupArray);
    }

    @Override
    public void onGroupItemCliched(String groupName) {
        Intent intent = new Intent(getContext().getApplicationContext(), GroupChatActivity.class);
        intent.putExtra("groupName",groupName);
        getContext().startActivity(intent);
    }
}
