package com.example.chatapp.adapters;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddFriendsToGroupsRecyclerAdapter  extends RecyclerView.Adapter<AddFriendsToGroupsRecyclerAdapter.UserViewHolder> {
    private ArrayList<UserInfo> userFrinds;

    public AddFriendsToGroupsRecyclerAdapter(AddFriendToChatInterfaec addFriendToChatInterfaec) {
        this.addFriendToChatInterfaec = addFriendToChatInterfaec;
    }

    private AddFriendToChatInterfaec addFriendToChatInterfaec;

    public void setUserFrinds(ArrayList<UserInfo> userFrinds) {
        this.userFrinds = userFrinds;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_friends_to_groub_list_item,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(v);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Picasso.get().load(userFrinds.get(position).getProfileIageUri()).placeholder(R.drawable.profile_image).into(holder.userProfileImage);
        holder.userName.setText(userFrinds.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        if(userFrinds == null) return 0;
        else return userFrinds.size();
    }

    public  class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView userProfileImage;
        TextView userName;
        Button addToGroub;
         public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImage= itemView.findViewById(R.id.userProfileImagex);
            userName= itemView.findViewById(R.id.userNamex);
            addToGroub = itemView.findViewById(R.id.addFriendToGroup);
            addToGroub.setOnClickListener(this::onClick);

        }

        @Override
        public void onClick(View v) {
             if(v.getId()==R.id.addFriendToGroup){
                 addFriendToChatInterfaec.OnFriendAdded(userFrinds.get(getAdapterPosition()));
             }
        }

    }
    public interface  AddFriendToChatInterfaec{
        public void OnFriendAdded(UserInfo user);
    }
}
