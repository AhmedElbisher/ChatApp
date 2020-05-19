package com.example.chatapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.model.UserInfo;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {


    private ArrayList<UserInfo> users= new ArrayList<UserInfo>();
    public   UsersListAdapter.UserClich mUserClich;
    private  boolean isReqeust= false;

    private boolean ischatfragment=false;
    public void setIsReqeust(boolean reqeust) {
        isReqeust = reqeust;
    }
    public void setIschatfragment(boolean ischatfragment) {
        this.ischatfragment = ischatfragment;
    }

    public UsersListAdapter(UsersListAdapter.UserClich userClich) {
      mUserClich =userClich;
    }

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

    public void setUsers(ArrayList<UserInfo> user) {
        this.users.clear();
        this.users.addAll(user);
        notifyDataSetChanged();
    }

    public  void  clearDate(){
        this.users.clear();
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserInfo currUser = users.get(position);

        if(isReqeust){
            holder.buttonslayouts.setVisibility(View.VISIBLE);
        }else{
            holder.buttonslayouts.setVisibility(View.GONE);
        }
        if(ischatfragment){
            holder.userStatusTextView.setText("last seen \nTime Date");
            holder.userStatusTextView.setTextColor(Color.rgb(128,128,128));
        }else{
            if(currUser.getUserStatus() != null){
                holder.userStatusTextView.setText(currUser.getUserStatus());
            }
        }
        if(currUser.getUserName() != null){
            holder.userNameTextView.setText(currUser.getUserName());
        }


        Picasso.get().load(currUser.getProfileIageUri()).placeholder(R.drawable.profile_image).into(holder.profileImage);
    }

    public  class  UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userNameTextView;
        TextView userStatusTextView;
        ImageView profileImage;
        LinearLayout buttonslayouts;
        Button accept;
        Button cancel;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView= itemView.findViewById(R.id.userName1);
            userStatusTextView = itemView.findViewById(R.id.userStatus1);
            profileImage = itemView.findViewById(R.id.profileImage1);
            buttonslayouts=itemView.findViewById(R.id.buttonsLinearLayout);
            accept=itemView.findViewById(R.id.acceptButton);
            cancel=itemView.findViewById(R.id.cancelButton);
            itemView.setOnClickListener(this);
            accept.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.acceptButton:
                    mUserClich.onAcceptClic(users.get(getAdapterPosition()));
                    users.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                case R.id.cancelButton:
                    mUserClich.onCancelClic(users.get(getAdapterPosition()));
                    users.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
              default:
                    mUserClich.onUserClick(users.get(getAdapterPosition()));

            }

        }
    }

    public  interface UserClich{
        public  void  onUserClick(UserInfo userInfo);
        public  void  onAcceptClic(UserInfo userInfo);
        public  void  onCancelClic(UserInfo userInfo);
    }
}
