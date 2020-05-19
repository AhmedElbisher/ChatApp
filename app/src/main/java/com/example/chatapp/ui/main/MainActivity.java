package com.example.chatapp.ui.main;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.chatapp.R;
import com.example.chatapp.adapters.ViewPagerAdapter;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.MainActivityInterface;
import com.example.chatapp.presenters.Presenter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        presenter = Presenter.getInstance();
        presenter.setMainActivityInterface(this);
        initToolBar();
        initViewPager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!presenter.islogin()) presenter.goToLoginActivity(this);
        presenter.verifyUserExistace();
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
            case  R.id.addGroup:
                requestNewGroup();
                break;
            case R.id.find_frinds:
                presenter.gotoFindFriends(this);
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void requestNewGroup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this ,R.style.AlerDailog);
        builder.setTitle("enter the group name");
        final EditText editText = new EditText(this);
        editText.setHintTextColor(Color.BLACK);
        editText.setHint(" group name");
        editText.setTextColor(Color.BLACK);
        builder.setView(editText);
        builder.setPositiveButton("create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName =  editText.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(MainActivity.this, "enter a valid name", Toast.LENGTH_SHORT).show();
                }else{
                    createNewGroup(groupName);
                }

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void createNewGroup(String groupName) {
        presenter.createGroup(groupName);

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


}
