package com.example.chatapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.R;
import com.example.chatapp.adapters.UsersListAdapter;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.FindRequestsInterface;
import com.example.chatapp.presenters.Presenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment implements FindRequestsInterface,UsersListAdapter.UserClich {
    RecyclerView reqeustRecyclerView;
    Presenter presenter;
    UsersListAdapter usersListAdapter;

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_blank, container, false);
        reqeustRecyclerView =v.findViewById(R.id.requestsRecyclerView);
        reqeustRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersListAdapter = new UsersListAdapter(this);
        usersListAdapter.setIsReqeust(true);
        reqeustRecyclerView.setAdapter(usersListAdapter);
        presenter=Presenter.getInstance();
        presenter.findRequests();
        presenter.setFindRequestsInterface(this);
        return v;
    }

    @Override
    public void OnFindReqeustsComplete(boolean isSucceded, ArrayList<UserInfo> userInfo) {
        if(isSucceded){
            usersListAdapter.setUsers(userInfo);
        }else{
            usersListAdapter.clearDate();
        }
    }

    @Override
    public void onUserClick(UserInfo userInfo) {
        Log.i("TAG", "onUserClick: ");
    }

    @Override
    public void onAcceptClic(UserInfo userInfo) {
        presenter.addfriend(userInfo.getUid(),false);
        Log.i("TAG", "onAcceptClic: ");
    }

    @Override
    public void onCancelClic(UserInfo userInfo) {
        Log.i("TAG", "onCancelClic: ");
        presenter.cancelMassageRequest(userInfo.getUid(),false);
    }
}
