package com.rap.rhythmandpoetry;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment{

    View myView;
    View myView1;
    LoginButton loginBtn;
    RoundImage roundedImage;
    static final String LOG_TAG = "BAD IMAGE";
    private TextView user_name, bio;
    private Button save;
    private ImageView profile;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("User");

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        user_name = (TextView) myView.findViewById(R.id.user_name);
        bio = (TextView) myView.findViewById(R.id.bio);
        profile = (ImageView) myView.findViewById(R.id.profile);
        myView = inflater.inflate(R.layout.profile_layout, container, false);
        final String key = currentFirebaseUser.getUid().toString();
        //  user_name.setText(mDatabase.);
        return myView;
        }}
