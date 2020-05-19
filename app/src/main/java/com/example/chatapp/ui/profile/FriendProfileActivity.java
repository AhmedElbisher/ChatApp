package com.example.chatapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.chatapp.R;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.FrindProfileInterface;
import com.example.chatapp.presenters.Presenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendProfileActivity extends AppCompatActivity implements View.OnClickListener, FrindProfileInterface {
    @BindView(R.id.friendprofile)
    CircleImageView profilePicture;
    @BindView(R.id.friendName)
    TextView name;
    @BindView(R.id.friendStatus)
    TextView status;
    @BindView(R.id.send_massage)
    Button sendButton;
    @BindView(R.id.sendCardView)
    CardView sendCardView;
    @BindView(R.id.cancelReqeust)
    Button cancelReqeustButton;
    @BindView(R.id.cancelCardView)
    CardView cancelCardView;
    private Presenter presenter;
    private UserInfo currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);
        init();
        presenter.checkRequestState(currUser.getUid());
        presenter.checkFriends(currUser.getUid());

    }

    private void init() {
        presenter = Presenter.getInstance();
        presenter.setFrindProfileInterface(this);
        Intent intent = getIntent();
        if (intent != null) {
            currUser = presenter.deserializeUserInfo(intent.getStringExtra("userInfo"));

        }
        sendButton.setOnClickListener(this);
        if (currUser.getUserName() != null) name.setText(currUser.getUserName());
        if (currUser.getUserStatus() != null) status.setText(currUser.getUserStatus());
        Picasso.get().load(currUser.getProfileIageUri()).placeholder(R.drawable.profile_image).into(profilePicture);
        cancelReqeustButton.setOnClickListener(this);
        cancelReqeustButton.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.send_massage) {
            String currentButtonText = sendButton.getText().toString();
            if (currentButtonText.equals(getString(R.string.send_massage_request))) {
                presenter.sendMassageRequest(currUser.getUid());
            }else if(currentButtonText.equals(getString(R.string.accept_massage_request))){
                presenter.addfriend(currUser.getUid(),true);
            }else if (currentButtonText.equals(getString(R.string.Remove_frind))){
                presenter.removeFriend(currUser.getUid());
            }
        }else  if(v.getId() == R.id.cancelReqeust){
            presenter.cancelMassageRequest(currUser.getUid(),true);
        }

    }

    @Override
    public void onSendMassageReqeustCompleted(boolean isSucceded, String error) {
        if (isSucceded && error.equals(getString(R.string.Reqest_sent_state))) {
            sendButton.setVisibility(View.GONE);
            cancelReqeustButton.setVisibility(View.VISIBLE);
        }else if(isSucceded && error.equals(getString(R.string.Reqest_recieve_state))){
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setText(R.string.accept_massage_request);
            cancelReqeustButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCancelMasssagrReqeustComplete(boolean isSucceded, String error) {
        if (isSucceded ) {
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setText(R.string.send_massage_request);
            cancelReqeustButton.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAcceptReqeustCompleted(boolean isSucceded) {
        if(isSucceded){
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setText(R.string.Remove_frind);
            cancelReqeustButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRemoveFriendCompleted(boolean isSucceded) {
        sendButton.setEnabled(true);
        cancelReqeustButton.setEnabled(true);
        if(isSucceded){
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setText(R.string.send_massage_request);
            cancelReqeustButton.setVisibility(View.GONE);
        }
    }
}
