package com.example.chatapp.ui.register;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.presenters.RegisterInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface, View.OnClickListener {

    @BindView(R.id.registerEmailText)
    EditText registerEmailText;
    @BindView(R.id.registerPasswordText)
    EditText registerPasswordText;
    @BindView(R.id.createAccountButton)
    Button createAccountButton;
    @BindView(R.id.alreadyhaveAccountText)
    TextView alreadyhaveAccountText;
    @BindView(R.id.progressbarLayout)
    LinearLayout progressbarLayout;

    private Presenter mPresenter;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = Presenter.getInstance();
        mPresenter.setRegisterInterface(this);
        alreadyhaveAccountText.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alreadyhaveAccountText:
                mPresenter.goToLoginActivity(this);
                break;
            case R.id.createAccountButton:
                progressbarLayout.setVisibility(View.VISIBLE);
                mPresenter.Register(registerEmailText.getText().toString(),
                        registerPasswordText.getText().toString());
                break;

            default:
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }

    }

    @Override
    public void onRegisterSuccess() {
        progressbarLayout.setVisibility(View.INVISIBLE);
        mPresenter.goToStings(this);
    }

    @Override
    public void onRegisterFailed(String error) {
        progressbarLayout.setVisibility(View.INVISIBLE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


}
