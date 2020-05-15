package com.example.chatapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.FrindProfileInterface;
import com.example.chatapp.presenters.Presenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendProfileActivity extends AppCompatActivity  implements View.OnClickListener , FrindProfileInterface {
    @BindView(R.id.friendprofile)
    CircleImageView profilePicture;
    @BindView(R.id.friendName)
    TextView name;
    @BindView(R.id.friendStatus)
    TextView status;
    @BindView(R.id.send_massage)
    Button sendButton;
    private Presenter presenter;
    private UserInfo currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);
        init();
        presenter.checkRequestState(currUser.getUid());

    }

    private void init() {
        presenter = Presenter.getInstance();
        presenter.setFrindProfileInterface(this);
        Intent intent = getIntent();
        if(intent != null){
           currUser = presenter.deserializeUserInfo(intent.getStringExtra("userInfo"));

        }
        sendButton.setOnClickListener(this);
        if(currUser.getUserName() != null) name.setText(currUser.getUserName());
        if(currUser.getUserStatus() != null) status.setText(currUser.getUserStatus());
        Picasso.get().load(currUser.getProfileIageUri()).placeholder(R.drawable.profile_image).into(profilePicture);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.send_massage){
            presenter.sendMassageRequest(currUser.getUid());
        }

    }

    @Override
    public void onSendMassageReqeustCompleted(boolean isSucceded, String error) {
        if(isSucceded){
            sendButton.setText("cancel request");
        }

    }
}
