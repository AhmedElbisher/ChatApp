package com.example.chatapp.ui.chats;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatapp.R;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsActivity extends AppCompatActivity {

    @BindView(R.id.chatToolBar)
    Toolbar chatToolBar;
    @BindView(R.id.editText)
    EditText sendEditText;
    @BindView(R.id.imageButton2)
    ImageButton SendImageButton;
    UserInfo friendInfo;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(chatToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter= Presenter.getInstance();
        Intent intent = getIntent();
        if(intent != null){
            friendInfo =presenter.deserializeUserInfo( intent.getStringExtra("userInfo"));
        }
        getSupportActionBar().setTitle(friendInfo.getUserName());
    }
}
