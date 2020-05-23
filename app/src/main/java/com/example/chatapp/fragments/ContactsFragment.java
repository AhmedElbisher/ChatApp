package com.example.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.UsersListAdapter;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.ContactsInterface;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.ui.chats.ChatsActivity;
import com.example.chatapp.ui.profile.FriendProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment implements UsersListAdapter.UserClich , ContactsInterface {


    UsersListAdapter usersListAdapter;
    RecyclerView recyclerView;
    Presenter presenter;

    public ContactsFragment() {

        // Required empty public constructor
    }

    @Override
    public void OnFindContactComplete(boolean isSucceded, ArrayList<UserInfo> userInfo) {
        if(isSucceded ) {
            usersListAdapter.setUsers(userInfo);
        }else {
            usersListAdapter.clearDate();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Inflate the layout for this fragment
        recyclerView= v.findViewById(R.id.friendsRecyclerView);
        presenter= Presenter.getInstance();
        presenter.setContactsInterface(this);
        usersListAdapter = new UsersListAdapter(this);
        usersListAdapter.setIschatfragment(true);
        recyclerView.setAdapter(usersListAdapter);
        if(presenter.islogin())presenter.findcontacts();
        else  presenter.goToLoginActivity(getContext());
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        //buttonsLinearLayout.setVisibility(View.GONE);

    }

    @Override
    public void onUserClick(UserInfo userInfo) {
        Intent intent = new Intent(getContext(), ChatsActivity.class);
        intent.putExtra("userInfo" ,presenter.serializeUserInfo(userInfo));
        startActivity(intent);
    }

    @Override
    public void onAcceptClic(UserInfo userInfo) {
    }

    @Override
    public void onCancelClic(UserInfo userInfo) {

    }
}
