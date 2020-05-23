package com.example.chatapp.ui.chats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.MessagesAdapter;
import com.example.chatapp.model.MassageDetails;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.presenters.Presenter;
import com.example.chatapp.presenters.SingleChatInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsActivity extends AppCompatActivity implements View.OnClickListener, SingleChatInterface {

    final static int IMAGE_INTENT = 555;
    public static String FILE_IMAGE = "image";
    public static String FILE_TEXT = "text";
    UserInfo friendInfo;
    Presenter presenter;
    String currentUserId;
    String friendId;
    CircleImageView profileImage1;
    TextView customUsername;
    TextView customLastSeen;
    @BindView(R.id.chatstoolbar)
    Toolbar chatsToolBAr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.chatsMassageEditText)
    EditText chatsMassageEditText;
    @BindView(R.id.chatsSendMassageImageView)
    ImageButton chatsSendMassageImageView;
    MessagesAdapter messagesAdapter;
    @BindView(R.id.sendFile)
    ImageButton sendFileOrImage;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    String fileType = "";
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        ButterKnife.bind(this);
        init();
    }

    private void init() {


        presenter = Presenter.getInstance();
        presenter.setSingleChatInterface(this);
        Intent intent = getIntent();
        if (intent != null) {
            friendInfo = presenter.deserializeUserInfo(intent.getStringExtra("userInfo"));
        }
        sendFileOrImage.setOnClickListener(this);
        initActionBar();
        currentUserId = presenter.getcurrentUserId();
        friendId = friendInfo.getUid();
        chatsSendMassageImageView.setOnClickListener(this);
        presenter.retreveSingleChatMassages(currentUserId, friendId);
        initRecyclerView();

    }

    private void initRecyclerView() {
        messagesAdapter = new MessagesAdapter(new ArrayList<MassageDetails>(), friendInfo.getProfileIageUri(), currentUserId);
        recyclerView.setAdapter(messagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initActionBar() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.custom_toolbar, null);
        profileImage1 = (CircleImageView) v.findViewById(R.id.custom_profileImage);
        customUsername = v.findViewById(R.id.custom_username);
        customLastSeen = v.findViewById(R.id.custom_lastSeen);
        customLastSeen.setText("Last seen");
        if (friendInfo.getUserName() != null) {
            customUsername.setText(friendInfo.getUserName());
            Picasso.get().load(friendInfo.getProfileIageUri()).placeholder(R.drawable.profile_image).into(profileImage1);
            String status = "";
            if (friendInfo.getState().equals("online")) {
                status = "online now";

            } else {
                status = "last seen:   " + friendInfo.getLastOnlinedate() + "   " + friendInfo.getLastOnlinetime();
            }
            customLastSeen.setText(status);
        }

        setSupportActionBar(chatsToolBAr);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(v);
        //  getSupportActionBar().setCustomView(R.layout.custom_toolbar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chatsSendMassageImageView) {
            String messageKey = presenter.getMessageKey(currentUserId, friendId);
            presenter.sendSingleChatMassage(chatsMassageEditText.getText().toString(), currentUserId, friendId, messageKey, FILE_TEXT);
            chatsMassageEditText.setText("");
        } else if (v.getId() == R.id.sendFile) {
            sendfile();

        }
    }

    private void sendfile() {
        CharSequence options[] = new CharSequence[]{
                "image",
                "pdf file",
                "ms file"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("whatis the file type");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        fileType = FILE_IMAGE;
                        getfilefromlocalStorage(fileType);
                        break;
                    default:
                        break;

                }
            }

            private void getfilefromlocalStorage(String fileType) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType(fileType + "/*");
                startActivityForResult(Intent.createChooser(intent, "get " + fileType), IMAGE_INTENT);
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            progressBar.setVisibility(View.VISIBLE);
            presenter.sendfiles(fileUri, fileType, presenter.getcurrentUserId(), friendInfo.getUid());
        }
    }

    @Override
    public void onSendMassagSucessed() {

    }

    @Override
    public void onSendMassagFailed(String error) {
        Toast.makeText(this, "failed to send this massage" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetrieveMassagesComplete(boolean succeded, ArrayList<MassageDetails> massages) {
        if (succeded && massages != null) {
            messagesAdapter.setMassages(massages);
            recyclerView.scrollToPosition(massages.size());
        }
        progressBar.setVisibility(View.GONE);

    }

}
