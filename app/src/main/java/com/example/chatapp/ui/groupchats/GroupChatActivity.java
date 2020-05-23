package com.example.chatapp.ui.groupchats;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatapp.R;
import com.example.chatapp.presenters.GroupActivityInterface;
import com.example.chatapp.presenters.Presenter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupChatActivity extends AppCompatActivity  implements GroupActivityInterface , View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView messagesTextView;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.imageButton)
    ImageButton sendButton;
    @BindView(R.id.editText2)
    EditText massageField;
    private String groupName;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        ButterKnife.bind(this);
        sendButton.setOnClickListener(this);
        init();
        presenter = Presenter.getInstance();
        presenter.setGroupActivityInterface(this);
        Log.i("TAG", Presenter.getUserNAme());


    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getGroupMassages(groupName);
        scrollView2.fullScroll(ScrollView.FOCUS_DOWN);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void  init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Group Chat");
        Intent intent = getIntent();
        if(intent != null){
            groupName=intent.getStringExtra("groupName");
            getSupportActionBar().setTitle(groupName);
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageButton){
            presenter.sendGroupMassage(massageField.getText().toString(),groupName);
            scrollView2.fullScroll(View.FOCUS_DOWN);
        }
    }

    @Override
    public void onSendMassageComplete(boolean isSuccedded, String Error) {
        if(isSuccedded){
            massageField.setText("");
        }else{
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRetreiveMassage(HashMap<String,String> massage) {
       messagesTextView.append(massage.get("name") + "\n"+
                       massage.get("massage") + "\n"+
                       massage.get("data") + "  "+
                       massage.get("time") + "\n\n\n"
               );
       scrollView2.fullScroll(ScrollView.FOCUS_DOWN);
    }
}
