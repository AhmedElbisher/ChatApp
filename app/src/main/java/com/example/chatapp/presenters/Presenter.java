package com.example.chatapp.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.chatapp.model.UserInfo;
import com.example.chatapp.ui.findfrends.FindFriendsActivity;
import com.example.chatapp.ui.login.LoginActivity;
import com.example.chatapp.ui.main.MainActivity;
import com.example.chatapp.ui.register.PhoneActivity;
import com.example.chatapp.ui.sitings.SittingsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.grpc.okhttp.internal.Util;

public class Presenter {
    //  when retrive data for user sitting it uses this const to identify which collection is retrived
    public  static final  int USER_STATUS =1;
    public  static final  int USER_only =1;
    public  static final  int USER_STATUS_IMAGE =2;
    public  static final  int NO_DATA =0;

    private FirebaseAuth mAuth;
    public static Presenter mPresenter;
    public FirebaseUser currentUser;
    private DatabaseReference mRef,mGroupRef;
    private static String userNAme;
    private FirebaseDatabase database;
    private LoginInterface loginInterface;
    private RegisterInterface registerInterface;
    private MainActivityInterface mainActivityInterface;
    private GroupActivityInterface groupActivityInterface;
    private GroupFragmentInterface groupFragmentInterface;
    private PhoneAuthProvider phoneAuthProvider;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private StorageReference mStorageReference;
    private Gson gson;
    private FindFrindsInterface findFrindsInterface;
    private  PhoneActivityInterface phoneActivityInterface;
    private SittingInterface sittingInterface;
    private FrindProfileInterface frindProfileInterface;
    private ContactsInterface contactsInterface;
    private  FindRequestsInterface findRequestsInterface;

    public void setFindRequestsInterface(FindRequestsInterface findRequestsInterface) {
       this.findRequestsInterface = findRequestsInterface;
    }
    public void setContactsInterface(ContactsInterface contactsInterface) {
        this.contactsInterface = contactsInterface;
    }

    public void setFrindProfileInterface(FrindProfileInterface frindProfileInterface) {
        this.frindProfileInterface = frindProfileInterface;
    }
    public void setFindFrindsInterface(FindFrindsInterface findFrindsInterface) {
        this.findFrindsInterface = findFrindsInterface;
    }
    public void setPhoneActivityInterface(PhoneActivityInterface phoneActivityInterface) {
        this.phoneActivityInterface = phoneActivityInterface;
    }
    public static String getUserNAme() {
        return userNAme;
    }
    public void setGroupActivityInterface(GroupActivityInterface groupActivityInterface) {
        this.groupActivityInterface = groupActivityInterface;
    }
    public void setGroupFragmentInterface(GroupFragmentInterface groupFragmentInterface) {
        this.groupFragmentInterface = groupFragmentInterface;
    }
    public void setSittingInterface(SittingInterface sittingInterface) {
        this.sittingInterface = sittingInterface;
    }
    public void setMainActivityInterface(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
    }
    public void setLoginInterface(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void setRegisterInterface(RegisterInterface registerInterface) {
        this.registerInterface = registerInterface;
    }

    private Presenter() {
        this.mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        phoneAuthProvider= PhoneAuthProvider.getInstance();
        gson= new Gson();
    }
    public static Presenter getInstance(){
        if (mPresenter == null) return new Presenter();
        else return mPresenter;
    }
    public void verifyUserExistace(){
        currentUser=mAuth.getCurrentUser();
        if(currentUser != null) {
            String userId = currentUser.getUid();
            mRef.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("name").exists()) {
                        if (dataSnapshot.exists() && dataSnapshot.hasChild("name")) {
                            userNAme = dataSnapshot.child("name").getValue().toString();
                        }
                        mainActivityInterface.onVerificationComplete(true);
                    } else {
                        mainActivityInterface.onVerificationComplete(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    public  String getcurrentUserId(){
        return mAuth.getCurrentUser().getUid();
    }
    public void UpdateuserInfo(String userName,String userStatus){
        String defualStatus = "I am here in chatAPP";
        if(!(TextUtils.isEmpty(userStatus))){ defualStatus = userStatus;}
        if(TextUtils.isEmpty(userName)){
            sittingInterface.onUpdateInfoComplete(false,"please inter valid username");

        }else{
            String userId = mAuth.getCurrentUser().getUid();
            Map<String, Object> profileInfo = new HashMap<String,Object>();
            profileInfo.put("uid",userId);
            profileInfo.put("name" , userName);
            profileInfo.put("status",defualStatus);
            mRef.child("users").child(userId).updateChildren(profileInfo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                sittingInterface.onUpdateInfoComplete(true,"");
                                Log.i("TAG", "onComplete: succeded ");

                            }else {
                                sittingInterface.onUpdateInfoComplete(false , task.getException().toString());
                                Log.i("TAG", "onComplete: failed ");
                            }

                        }
                    });

        }

    }
    public void retreiveUserInfo(){
        String userId = mAuth.getCurrentUser().getUid();
        mRef.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.exists()) && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status")
                && dataSnapshot.hasChild("image")){
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String status = dataSnapshot.child("status").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    sittingInterface.onRetreiveUserData(USER_STATUS_IMAGE ,userName,status,image );
                }else if((dataSnapshot.exists()) && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status")){
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String status = dataSnapshot.child("status").getValue().toString();
                    sittingInterface.onRetreiveUserData(USER_STATUS ,userName,status,"" );
                }else if((dataSnapshot.exists()) && dataSnapshot.hasChild("name")){
                    String userName = dataSnapshot.child("name").getValue().toString();
                    sittingInterface.onRetreiveUserData(USER_only ,userName,"i am here in charAPP","" );
                }else{
                    sittingInterface.onRetreiveUserData(NO_DATA ,"","","" );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void retreiveUsers(){
        mRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserInfo curentUser = new UserInfo();
                if(dataSnapshot.exists()){
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DataSnapshot dataSnapshot1 =(DataSnapshot) iterator.next();
                        if(dataSnapshot1.getKey().equals("name")){
                            curentUser.setUserName(dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("image")){
                            curentUser.setProfileIageUri(dataSnapshot1.getValue().toString());
                        }if(dataSnapshot1.getKey().equals("status")){
                            curentUser.setUserStatus(dataSnapshot1.getValue().toString());
                        }

                        if(dataSnapshot1.getKey().equals("uid")){
                            curentUser.setUid(dataSnapshot1.getValue().toString());
                        }
                    }
                    findFrindsInterface.onRetreiveUser(curentUser);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserInfo curentUser = new UserInfo();
                if(dataSnapshot.exists()){
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DataSnapshot dataSnapshot1 =(DataSnapshot) iterator.next();
                        if(dataSnapshot1.getKey().equals("name")){
                            curentUser.setUserName(dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("image")){
                            curentUser.setProfileIageUri(dataSnapshot1.getValue().toString());
                        }if(dataSnapshot1.getKey().equals("status")){
                            curentUser.setUserStatus(dataSnapshot1.getValue().toString());
                        }
                    }
                    findFrindsInterface.onRetreiveUser(curentUser);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void SignOut(){
        mAuth.signOut();
    }
    public boolean islogin(){
        if(currentUser != null)return true;
        else return false;
    }
    public  void  Register(String email , String password ){
        if(TextUtils.isEmpty(email)){
            registerInterface.onRegisterFailed("email field is empty");
        }else if(TextUtils.isEmpty(password)){
            registerInterface.onRegisterFailed("password field is empty");
        }else{
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                currentUser=mAuth.getCurrentUser();
                                String userId = currentUser.getUid();
                                mRef.child("users").child(userId).setValue("");
                                registerInterface.onRegisterSuccess();
                            }else{
                                registerInterface.onRegisterFailed("Error" + task.getException());
                            }
                        }
                    });
        }
    }
    public void logIn(String email,String password){
        if(TextUtils.isEmpty(email)){
            loginInterface.onLogInFailed("email field is empty");
        }else if(TextUtils.isEmpty(password)){
            loginInterface.onLogInFailed("password field is empty");
        }else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                currentUser = mAuth.getCurrentUser();
                                loginInterface.onLogInSuccedded();
                            }else{
                                loginInterface.onLogInFailed(task.getException().toString());

                            }

                        }
                    });
        }

    }
    public  void  createGroup(String groupName){
        mRef.child("groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mainActivityInterface.onCreatGroupComplete(true);
                        }else{
                            mainActivityInterface.onCreatGroupComplete(false);
                        }
                    }
                });
    }
    public  void  retreiveGroupsNames(){
        mRef.child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> groupsSet = new HashSet<>();
                ArrayList<String > result = new ArrayList<>();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    groupsSet.add(iterator.next().getKey());
                }
                result.addAll(groupsSet);
                groupFragmentInterface.onGroupsArrayChange(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void  sendGroupMassage(String massage,String groupName){
        mGroupRef= mRef.child("groups").child(groupName);
        if(TextUtils.isEmpty(massage)){
            groupActivityInterface.onSendMassageComplete(false,"please enter valid massage");
        }else{
            String date = getDate();
            String time = gettime();
            HashMap<String,Object> massageinfo = new HashMap<>();
            massageinfo.put("userName" , userNAme);
            massageinfo.put("massage",massage);
            massageinfo.put("date",date);
            massageinfo.put("time",time);

            mGroupRef.push().updateChildren(massageinfo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                groupActivityInterface.onSendMassageComplete(true,"");
                            }else{
                                groupActivityInterface.onSendMassageComplete(false,task.getException().toString());
                            }
                        }
                    });
        }
    }
    public void  getGroupMassages(String groupname){
        mRef.child("groups").child(groupname).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                HashMap<String,String> result = new HashMap<>();
                if(dataSnapshot.exists()){
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DataSnapshot dataSnapshot1 =(DataSnapshot) iterator.next();
                        result.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                    }
                    groupActivityInterface.onRetreiveMassage(result);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                HashMap<String,String> result = new HashMap<>();
                if(dataSnapshot.exists()){
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DataSnapshot dataSnapshot1 =(DataSnapshot) iterator.next();
                        result.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                    }
                    groupActivityInterface.onRetreiveMassage(result);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void  senduserVerificationCode(String phoneNumber, PhoneActivity activity){
        if(TextUtils.isEmpty(phoneNumber)){
            phoneActivityInterface.onVrerificationFailed("enter a valid phone numder");
        }else {
            phoneAuthProvider.verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    activity,               // Activity (for callback binding)
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            phoneActivityInterface.onVrerificationComplete(phoneAuthCredential);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            phoneActivityInterface.onVrerificationFailed(e.toString());
                        }


                        @Override
                        public void onCodeSent(@NonNull String verificationId,
                                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

                            mVerificationId = verificationId;
                            mResendToken = token;
                            phoneActivityInterface.oncodeSent();

                            // ...
                        }
                    });// OnVerificationStateChangedCallbacks

        }

    }
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final PhoneActivity activity) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = task.getResult().getUser();
                            phoneActivityInterface.onSignInWithPhoneCompete(true);
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Log.i("TAG", "onComplete: failed");
                            }
                            phoneActivityInterface.onSignInWithPhoneCompete(false);
                        }
                    }
                });
    }
    public void verifyPhoneNumber(String verficationCode,PhoneActivity activity){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verficationCode);
        signInWithPhoneAuthCredential(credential,activity);

    }
    public  void  saveImage(Uri photoUri){
        mStorageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");
        mStorageReference.child(currentUser.getUid()+".png").putFile(photoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        URL imageUrl = null;
                        try {
                           imageUrl= new URL(downloadUrl.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        sittingInterface.OnUploadImageSucceded(true ,imageUrl.toString());

                        mRef.child("users").child(currentUser.getUid()).child("image").setValue(imageUrl.toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            sittingInterface.OnSaveImageTOUserDAtaSucceded(true);

                                        }else {
                                            sittingInterface.OnSaveImageTOUserDAtaSucceded(false);
                                        }
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sittingInterface.OnUploadImageSucceded(false,null);
            }
        });

    }
    public void checkRequestState(String  senderId){
        mRef.child("requestes").child(senderId).child(currentUser.getUid())
                .child("tybe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String state = dataSnapshot.getValue().toString();
                    if(state.equals("sent")) frindProfileInterface.onSendMassageReqeustCompleted(true,"sent");
                    else{
                        frindProfileInterface.onSendMassageReqeustCompleted(true,"recieved");
                    }
                }else{
                    frindProfileInterface.onSendMassageReqeustCompleted(false,"");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void  sendMassageRequest(String senderId){
        mRef.child("requestes").child(senderId).child(currentUser.getUid())
                .child("tybe").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           mRef.child("requestes").child(currentUser.getUid()).child(senderId)
                                   .child("tybe").setValue("recieved")
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               frindProfileInterface.onSendMassageReqeustCompleted(true , "sent");

                                           }else{
                                               frindProfileInterface.onSendMassageReqeustCompleted(false,"can not send reciered reqaust");
                                           }
                                       }
                                   });
                       }else{
                           frindProfileInterface.onSendMassageReqeustCompleted(false,"can not send a sent reqaust");

                       }
                    }
                });

    }
    public void cancelMassageRequest(String senderId,boolean activeCallBack){
        mRef.child("requestes").child(senderId).child(currentUser.getUid())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mRef.child("requestes").child(currentUser.getUid()).child(senderId)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                if(activeCallBack) frindProfileInterface.onCancelMasssagrReqeustComplete(true ,"");

                                            }else{
                                               if(activeCallBack) frindProfileInterface.onCancelMasssagrReqeustComplete(false,"can not send reciered reqaust");
                                            }
                                        }
                                    });
                        }else{
                            if(activeCallBack) frindProfileInterface.onSendMassageReqeustCompleted(false,"can not send a sent reqaust");

                        }
                    }
                });

    }
    public void addfriend(String  senderId,boolean activeCallback){
        mRef.child("contacts").child(senderId).child(currentUser.getUid())
                .child("cantact").setValue("saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mRef.child("contacts").child(currentUser.getUid()).child(senderId)
                                    .child("cantact").setValue("saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                               if(activeCallback) frindProfileInterface.onAcceptReqeustCompleted(true);
                                            }else
                                            {
                                                if(activeCallback)frindProfileInterface.onAcceptReqeustCompleted(false);

                                            }

                                        }
                                    });

                        }
                    }
                });

        cancelMassageRequest(senderId,false);
    }
    public void removeFriend(String friendId){
        mRef.child("contacts").child(friendId).child(currentUser.getUid())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mRef.child("contacts").child(currentUser.getUid()).child(friendId)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                frindProfileInterface.onRemoveFriendCompleted(true);
                                            }else
                                            {
                                                frindProfileInterface.onRemoveFriendCompleted(false);

                                            }

                                        }
                                    });
                        }
                    }
                });
    }
    public  void  checkFriends(String friendId){
        mRef.child("contacts").child(friendId).child(currentUser.getUid())
                .child("cantact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String cantactState = dataSnapshot.getValue().toString();
                    if(cantactState.equals("saved")) frindProfileInterface.onAcceptReqeustCompleted(true);
                    else{
                        frindProfileInterface.onAcceptReqeustCompleted(false);                    }
                }else{
                    frindProfileInterface.onAcceptReqeustCompleted(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void findcontacts(){
        mRef.child("contacts").child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<UserInfo> result = new ArrayList<>();
                        if(dataSnapshot.exists()){
                            Iterator iterator = dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()){
                                String friendId = ((DataSnapshot)iterator.next()).getKey();
                                findUserInfoById(result,friendId,"contents");
                            }
                        }else {
                           contactsInterface.OnFindContactComplete(false,null);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public  void  findRequests(){
        mRef.child("requestes").child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<UserInfo> result = new ArrayList<>();
                        if(dataSnapshot.exists()){
                            Iterator iterator = dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()){
                                DataSnapshot dataSnapshot1 = (DataSnapshot)iterator.next();
                                String requesttybe = dataSnapshot1.child("tybe").getValue().toString();
                                String friendId = dataSnapshot1.getKey();
                                if(requesttybe.equals("sent")) findUserInfoById(result,friendId,"req");
                            }
                        }else {
                           findRequestsInterface.OnFindReqeustsComplete(false,null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public  void  findUserInfoById(ArrayList<UserInfo> result,String friendId,String funName){
        mRef.child("users").child(friendId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator iterator = dataSnapshot.getChildren().iterator();
                        UserInfo userInfo = new UserInfo();
                        if(dataSnapshot.exists()){
                            userInfo.setUserStatus(dataSnapshot.child("status").getValue().toString());
                            if(dataSnapshot.child("image").getValue() != null) {
                                userInfo.setProfileIageUri(dataSnapshot.child("image").getValue().toString());
                            }
                            userInfo.setUserName(dataSnapshot.child("name").getValue().toString());
                            userInfo.setUid(friendId);
                            result.add(userInfo);
                            if(funName.equals("contents"))  contactsInterface.OnFindContactComplete(true,result);
                            else if(funName.equals("req")) findRequestsInterface.OnFindReqeustsComplete(true,result);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }



    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }
    private String gettime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        return simpleDateFormat.format(calendar.getTime());
    }

    public  void goToLoginActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(intent);
    }
    public  void goToStings(Context context){
        Intent intent = new Intent(context.getApplicationContext(), SittingsActivity.class);
        context.startActivity(intent);
    }
    public void gotoFindFriends(Context context){
        Intent intent = new Intent(context.getApplicationContext(), FindFriendsActivity.class);
        context.startActivity(intent);
    }
    public  void goToMainActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public void goToPhonrActivity(Context context){
        Intent intent = new Intent(context.getApplicationContext(), PhoneActivity.class);
        context.startActivity(intent);
    }

    public String serializeUserInfo(UserInfo userInfo){
        return gson.toJson(userInfo);

    }
    public UserInfo deserializeUserInfo(String json){
        return gson.fromJson(json,UserInfo.class);
    }
}
