package com.example.chatapp.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.chatapp.R;
import com.example.chatapp.adapters.ViewPagerAdapter;
import com.example.chatapp.fragments.ContactsFragment;
import com.example.chatapp.fragments.GroupFragment;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.GroupFragmentInterface;
import com.example.chatapp.presenters.MainActivityInterface;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.ui.groupchats.GroupChatActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainActivityInterface , ContactsFragment.ContactesFragmentInterface, GroupFragment.GroupFragmentInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Presenter presenter;
    ArrayList<UserInfo> userFrinds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userFrinds = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        presenter = Presenter.getInstance();
        if(!presenter.islogin()) presenter.goToLoginActivity(this);
        presenter.setMainActivityInterface(this);
        initToolBar();
        presenter.verifyUserExistace();
        initViewPager();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(presenter.islogin()){
            presenter.updateUserState("online");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(presenter.islogin()){
            presenter.updateUserState("offline");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter.islogin()){
            presenter.updateUserState("offline");
        }
    }

    void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.getString(R.string.app_name));
    }
    void initViewPager() {

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                presenter.SignOut();
                presenter.goToLoginActivity(this);
                break;
            case  R.id.stings:
                presenter.goToStings(this);
                break;

            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onVerificationComplete(boolean userExsist) {
        if(!userExsist){
            presenter.goToStings(this);
        }
    }

    @Override
    public void onCreatGroupComplete(boolean isSucceded) {
        if(isSucceded){
            Toast.makeText(this, "new group created", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "new group created", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onRetreiveUserFrinds(ArrayList<UserInfo> usersInfo) {
        userFrinds.clear();
        userFrinds.addAll(usersInfo);
    }

    @Override
    public void goToGroupChatACtivity(String groupName) {
        Gson gson = new Gson();
        String userFrindsString = gson.toJson(userFrinds);
        Intent intent = new Intent(getApplicationContext(), GroupChatActivity.class);
        intent.putExtra("groupName",groupName);
        intent.putExtra("friends" , userFrindsString);
        startActivity(intent);
    }
}
