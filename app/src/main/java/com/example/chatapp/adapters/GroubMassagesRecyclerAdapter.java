package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.model.MassageDetails;
import com.example.chatapp.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroubMassagesRecyclerAdapter extends RecyclerView.Adapter<GroubMassagesRecyclerAdapter.MassageViewHolder> {


    private UserInfo currentUserInfo;

    public void setMassages(ArrayList<MassageDetails> massages) {
        this.massages.clear();
        this.massages.addAll(massages);
        notifyDataSetChanged();
    }

    private ArrayList<MassageDetails> massages;

    public GroubMassagesRecyclerAdapter(UserInfo currentUserInfo, ArrayList<MassageDetails> massages) {
        this.massages = new ArrayList<>();
        this.currentUserInfo = new UserInfo();
        this.massages.addAll(massages);
    }
    public void setCurrentUserInfo(UserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }

    @NonNull
    @Override
    public GroubMassagesRecyclerAdapter.MassageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.groub_massage_layout,parent,false);
        return new MassageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroubMassagesRecyclerAdapter.MassageViewHolder holder, int position) {
        MassageDetails currentMassage = massages.get(position);
        if(currentMassage.getSenderId().equals(currentUserInfo.getUid())){
            holder.senderProfilePhoto.setVisibility(View.GONE);
            holder.otherMassageTextView.setVisibility(View.GONE);
            holder.ownerMassageTextView.setVisibility(View.VISIBLE);
            holder.ownerMassageTextView.setText(currentMassage.getMassageText());
        }else {
            holder.senderProfilePhoto.setVisibility(View.VISIBLE);
            holder.otherMassageTextView.setVisibility(View.VISIBLE);
            holder.ownerMassageTextView.setVisibility(View.GONE);
            Picasso.get().load(currentMassage.getSenderPhotoUrl()).placeholder(R.drawable.profile_image).into(holder.senderProfilePhoto);
            holder.otherMassageTextView.setText(currentMassage.getMassageText());
        }

    }

    @Override
    public int getItemCount() {
        if(massages ==null) return  0;
        return massages.size();
    }

    public  class  MassageViewHolder extends RecyclerView.ViewHolder{
        TextView otherMassageTextView;
        TextView ownerMassageTextView;
        ImageView senderProfilePhoto;


        public MassageViewHolder(@NonNull View itemView) {
            super(itemView);
            otherMassageTextView = itemView.findViewById(R.id.senderMassage);
            ownerMassageTextView= itemView.findViewById(R.id.ownerMassage);
            senderProfilePhoto=itemView.findViewById(R.id.senderPhoto);
        }
    }
}
