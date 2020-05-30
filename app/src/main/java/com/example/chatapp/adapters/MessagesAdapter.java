package com.example.chatapp.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.model.MassageDetails;
import com.example.chatapp.ui.chats.ChatsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {



    private ArrayList<MassageDetails> massages;
    private String friendProfileImageUrl;
    private String currentUserId;

    public MessagesAdapter(ArrayList<MassageDetails> massages, String friendUrl, String currentUserId) {
        this.massages = massages;
        this.friendProfileImageUrl = friendUrl;
        this.currentUserId = currentUserId;

    }

    public void setMassages(ArrayList<MassageDetails> massages) {
        this.massages.clear();
        this.massages.addAll(massages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_massage_layout, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MassageDetails currentMassage =  massages.get(position);
        if(currentMassage.getSenderId().equals(currentUserId)){
            setVisabilityofviews(true,currentMassage.getType() ,holder);
            if(currentMassage.getType().equals(ChatsActivity.FILE_TEXT)) {
                holder.userMassageText.setText(currentMassage.getMassageText());
            }else{
                if(currentMassage.getType().equals(ChatsActivity.FILE_IMAGE)) {
                    Picasso.get().load(currentMassage.getMassageText()).placeholder(R.drawable.file).into(holder.useMassageImage);
                }else{
                    holder.useMassageImage.setImageResource(R.drawable.file);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentMassage.getMassageText()));
                            v.getContext().startActivity(intent);
                        }
                    });
                }
            }
        }else{
            setVisabilityofviews(false,currentMassage.getType() ,holder);
            Picasso.get().load(friendProfileImageUrl).placeholder(R.drawable.profile_image).into(holder.friendProfileImage);
            if(currentMassage.getType().equals(ChatsActivity.FILE_TEXT)) {
                holder.friendMassageText.setText(currentMassage.getMassageText());
            }else{
                if(currentMassage.getType().equals(ChatsActivity.FILE_IMAGE)) {
                    Picasso.get().load(currentMassage.getMassageText()).placeholder(R.drawable.file).into(holder.friendMassageImage);
                }else{
                    holder.friendMassageImage.setImageResource(R.drawable.file);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentMassage.getMassageText()));
                            v.getContext().startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    private void setVisabilityofviews(boolean iscurrentUserMassage, String type , MessageViewHolder holder) {
        if( iscurrentUserMassage ){
            if(type.equals(ChatsActivity.FILE_TEXT)){
                holder.friendProfileImage.setVisibility(View.GONE);
                holder.friendMassageText.setVisibility(View.GONE);
                holder.friendMassageImage.setVisibility(View.GONE);
                holder.useMassageImage.setVisibility(View.GONE);
                holder.userMassageText.setVisibility(View.VISIBLE);
            }else{
                holder.friendProfileImage.setVisibility(View.GONE);
                holder.friendMassageText.setVisibility(View.GONE);
                holder.friendMassageImage.setVisibility(View.GONE);
                holder.useMassageImage.setVisibility(View.VISIBLE);
                holder.userMassageText.setVisibility(View.GONE);
            }

        }else{
            holder.friendProfileImage.setVisibility(View.VISIBLE);
            if(type.equals(ChatsActivity.FILE_TEXT)) {
                holder.friendMassageText.setVisibility(View.VISIBLE);
                holder.userMassageText.setVisibility(View.GONE);
                holder.friendMassageImage.setVisibility(View.GONE);
                holder.useMassageImage.setVisibility(View.GONE);
            }else {
                holder.friendMassageText.setVisibility(View.GONE);
                holder.userMassageText.setVisibility(View.GONE);
                holder.friendMassageImage.setVisibility(View.VISIBLE);
                holder.useMassageImage.setVisibility(View.GONE);
            }

        }


    }

    @Override
    public int getItemCount() {
        if (massages != null) return massages.size();
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView friendProfileImage;
        TextView friendMassageText;
        TextView userMassageText;
        ImageView useMassageImage;
        ImageView friendMassageImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            friendMassageText= itemView.findViewById(R.id.senderMassageText);
            userMassageText= itemView.findViewById(R.id.recievedMassageText);
            friendProfileImage= itemView.findViewById(R.id.massageImage);
            useMassageImage = itemView.findViewById(R.id.messageImagereciever);
            friendMassageImage= itemView.findViewById(R.id.messageImageSender);
        }
    }
}
