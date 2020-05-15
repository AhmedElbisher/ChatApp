package com.example.chatapp.ui.register;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.chatapp.R;
import com.example.chatapp.presenters.PhoneActivityInterface;
import com.example.chatapp.presenters.Presenter;
import com.google.firebase.auth.PhoneAuthCredential;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener, PhoneActivityInterface {


    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @BindView(R.id.phoneNumberCard)
    CardView phoneNumberCard;
    @BindView(R.id.verificationCodeEditText)
    EditText verificationCodeEditText;
    @BindView(R.id.verificationCodeTextCard)
    CardView verificationCodeTextCard;
    @BindView(R.id.sendCodeButton)
    Button sendCodeButton;
    @BindView(R.id.sendCodeButtonCard)
    CardView sendCodeButtonCard;
    @BindView(R.id.verifyButton)
    Button verifyButton;
    @BindView(R.id.verifyButtonCard)
    CardView verifyButtonCard;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);
        sendCodeButton.setOnClickListener(this);
        verifyButton.setOnClickListener(this);
        presenter = Presenter.getInstance();
        presenter.setPhoneActivityInterface(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendCodeButton) {
            presenter.senduserVerificationCode(phoneNumberEditText.getText().toString(), this);
        } else if (v.getId() == R.id.verifyButton) {
            presenter.verifyPhoneNumber(verificationCodeEditText.getText().toString(), this);
        }

    }

    @Override
    public void onVrerificationComplete(PhoneAuthCredential phoneAuthCredential) {
        presenter.signInWithPhoneAuthCredential(phoneAuthCredential, this);
        Toast.makeText(this, " complete", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onVrerificationFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void oncodeSent() {
        Toast.makeText(this, " code send", Toast.LENGTH_SHORT).show();
        changeViewsVisibility(false);


    }

    @Override
    public void onSignInWithPhoneCompete(boolean isSuccedded) {
        if (isSuccedded) {
            presenter.goToStings(this);
        }

    }
    //when the code is sent make the verify button and edit text appers
    public  void changeViewsVisibility(boolean phoneNumberMode){
        if(!phoneNumberMode){
            phoneNumberCard.setVisibility(View.GONE);
            sendCodeButtonCard.setVisibility(View.GONE);
            verificationCodeTextCard.setVisibility(View.VISIBLE);
            verifyButtonCard.setVisibility(View.VISIBLE);
        }else{
            phoneNumberCard.setVisibility(View.VISIBLE);
            sendCodeButtonCard.setVisibility(View.VISIBLE);
            verificationCodeTextCard.setVisibility(View.GONE);
            verifyButtonCard.setVisibility(View.GONE);
        }

    }
}
