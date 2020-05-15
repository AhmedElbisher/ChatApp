package com.example.chatapp.ui.sitings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.chatapp.R;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.presenters.SittingInterface;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SittingsActivity extends AppCompatActivity implements View.OnClickListener, SittingInterface {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userNameCard)
    CardView userNameCard;
    @BindView(R.id.userStatus)
    EditText userStatus;
    @BindView(R.id.statusCard)
    CardView statusCard;
    @BindView(R.id.update)
    Button updateButton;
    Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitings);
        ButterKnife.bind(this);
        presenter = Presenter.getInstance();
        presenter.setSittingInterface(this);
        updateButton.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        presenter.retreiveUserInfo();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update) {
            presenter.UpdateuserInfo(userName.getText().toString()
                    , userStatus.getText().toString());

        }
        if (v.getId() == R.id.profile_image) {
            getPhoto();

        }
    }

    private void getPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                presenter.saveImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

    @Override
    public void onUpdateInfoComplete(boolean updateDone, String Error) {

        if (updateDone) {
            presenter.goToMainActivity(this);
        } else {
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRetreiveUserData(int mode, String userName, String userStatus, String ImageUrl) {
        if (mode == Presenter.USER_STATUS_IMAGE) {
            this.userName.setText(userName);
            this.userStatus.setText(userStatus);
            Picasso.get().load(ImageUrl).into(profileImage);
        } else if (mode == Presenter.USER_STATUS || mode == Presenter.USER_only) {
            this.userName.setText(userName);
            this.userStatus.setText(userStatus);
        }
    }

    @Override
    public void OnUploadImageSucceded(boolean isSucceded) {
        Toast.makeText(this, "Image Uploaded succedssfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSaveImageTOUserDAtaSucceded(boolean isSucceded) {
        Toast.makeText(this, "Image saved to your data", Toast.LENGTH_SHORT).show();
    }
}
