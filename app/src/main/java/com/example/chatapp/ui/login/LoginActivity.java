package com.example.chatapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.example.chatapp.presenters.LoginInterface;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.ui.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginInterface, View.OnClickListener {

    @BindView(R.id.loginEmail)
    EditText loginEmail;
    @BindView(R.id.LoginPassword)
    EditText LoginPassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.loginByPhone)
    Button loginByPhone;
    Presenter presenter;
    @BindView(R.id.needAccount)
    Button needAccount;
    @BindView(R.id.progressbarLayout)
    LinearLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = Presenter.getInstance();
        presenter.setLoginInterface(this);
        loginByPhone.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        needAccount.setOnClickListener(this);

    }

    public void goToRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }


    @Override
    public void onLogInSuccedded() {
        progressBar.setVisibility(View.GONE);
        presenter.goToMainActivity(this);

    }

    @Override
    public void onLogInFailed(String error) {
        progressBar.setVisibility(View.GONE);
        int index = error.indexOf(" ");
        Toast.makeText(this, error.substring(index, error.length()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                progressBar.setVisibility(View.VISIBLE);
                presenter.logIn(loginEmail.getText().toString(), LoginPassword.getText().toString());
                break;
            case R.id.needAccount:
                goToRegisterActivity();
                break;
            case R.id.loginByPhone:
                presenter.goToPhonrActivity(this);
                break;
            default:
        }

    }
}
