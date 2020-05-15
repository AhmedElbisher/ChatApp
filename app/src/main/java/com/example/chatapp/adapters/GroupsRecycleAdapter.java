package com.example.chatapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GroupsRecycleAdapter extends RecyclerView.Adapter<GroupsRecycleAdapter.GroupsViewHolder>{

    private ArrayList<String> groupArray = new ArrayList<>() ;
    private GroupsRecycleAdapter.GroupClickedInterface groupClickedInterface;

    public GroupsRecycleAdapter(GroupsRecycleAdapter.GroupClickedInterface fragementInterface ) {
        groupClickedInterface = fragementInterface;
    }

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate( R.layout.groups_list_item,parent,false);
        GroupsViewHolder groupsViewHolder = new GroupsViewHolder(view);
        return groupsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, int position) {
        holder.textView.setText(groupArray.get(position));
        Log.i("TAG", "setGroupArray: "+ this.groupArray.toString());

    }

    public void setGroupArray(ArrayList<String> groupArray) {
        this.groupArray.clear();
        this.groupArray.addAll(groupArray);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if(groupArray == null) return 0;
        else return  groupArray.size();
    }

    public class  GroupsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        public GroupsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.groupNameTextView);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            groupClickedInterface.onGroupItemCliched(groupArray.get(getLayoutPosition()));
        }
    }

    public  interface  GroupClickedInterface{
        public void  onGroupItemCliched(String groubName);

    }
}
