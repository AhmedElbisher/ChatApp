package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {


    private ArrayList<UserInfo> users= new ArrayList<UserInfo>();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list_item,parent,false);
        return new UserViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if(users != null) return users.size();
        return 0;
    }

    public void setUsers(UserInfo user) {
        this.users.add(user);
        notifyDataSetChanged();
    }

    public  void  clearDate(){
        this.users.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserInfo currUser = users.get(position);
        if(currUser.getUserName() != null){
            holder.userNameTextView.setText(currUser.getUserName());
        }
        if(currUser.getUserStatus() != null){
            holder.userStatusTextView.setText(currUser.getUserStatus());
        }

        Picasso.get().load(currUser.getProfileIageUri()).placeholder(R.drawable.profile_image).into(holder.profileImage);
    }

    public  class  UserViewHolder extends RecyclerView.ViewHolder{
        TextView userNameTextView;
        TextView userStatusTextView;
        ImageView profileImage;



        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView= itemView.findViewById(R.id.userName1);
            userStatusTextView = itemView.findViewById(R.id.userStatus1);
            profileImage = itemView.findViewById(R.id.profileImage1);
        }
    }
}
