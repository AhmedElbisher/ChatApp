package com.example.chatapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.GroupsRecycleAdapter;
import com.example.chatapp.presenters.GroupFragmentInterface;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.ui.main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.type.Color;

import java.util.ArrayList;

import butterknife.BindView;


public class GroupFragment extends Fragment implements GroupFragmentInterface, GroupsRecycleAdapter.GroupClickedInterface, View.OnClickListener {


    RecyclerView.LayoutManager layoutManager;
    GroupsRecycleAdapter adapter;
    Presenter presenter;
    RecyclerView recyclerView;
    GroupFragmentInterface groupFragmentInterface;
    FloatingActionButton addGroup;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        groupFragmentInterface = (GroupFragmentInterface) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        groupFragmentInterface = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(presenter.islogin())presenter.retreiveGroupsNames();
    }

    public GroupFragment() {
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addGroup){
            requestNewGroup();
        }
    }

    public interface GroupFragmentInterface {
        public void goToGroupChatACtivity(String groupName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        initRecylerView(view);
        presenter = Presenter.getInstance();
        presenter.setGroupFragmentInterface(this);
        addGroup = view.findViewById(R.id.addGroup);
        addGroup.setOnClickListener(this);

        return view;
    }

    private void initRecylerView(View v) {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = v.findViewById(R.id.grouprecyclerView);
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
        groupFragmentInterface.goToGroupChatACtivity(groupName);
    }

    private void requestNewGroup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext() ,R.style.AlerDailog);
        builder.setTitle("enter the group name");
        final EditText editText = new EditText(getContext());
        editText.setHint(" group name");
        editText.setTextColor(getResources().getColor(R.color.colorAccent));
        builder.setView(editText);
        builder.setPositiveButton("create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName =  editText.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(getContext(), "enter a valid name", Toast.LENGTH_SHORT).show();
                }else{
                    createNewGroup(groupName);
                }

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void createNewGroup(String groupName) {
        presenter.createGroup(groupName);

    }

}
