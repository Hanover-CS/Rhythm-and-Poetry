package com.rap.rhythmandpoetry;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileFragment extends Fragment{

    View myView;
    StorageReference storage;
    LoginButton loginBtn;
    RoundImage roundedImage;
    static final String LOG_TAG = "BAD IMAGE";
    private ImageView profile;
    private ArrayList<String> userPoems = new ArrayList<>();
    String data,key2;


    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    final String key = currentFirebaseUser.getUid().toString();

    DatabaseReference myRef = mDatabase.getReference("User Poems").child(key);
    DatabaseReference myRef2 = mDatabase.getReference("User");


    // Create a reference with an initial file path and name


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);
        final TextView userName = (TextView) myView.findViewById(R.id.user_name);
        final TextView bio = (TextView) myView.findViewById(R.id.bio);
        final ImageView profileView = (ImageView) myView.findViewById(R.id.profile);
        storage = FirebaseStorage.getInstance().getReference();
        ListView PoemsList = (ListView)myView.findViewById(R.id.poems);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, userPoems);
        PoemsList.setAdapter(arrayAdapter);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()){
                    String username = (String) (messageSnapshot.child("User name").getValue());
                    String BIO = (String) (messageSnapshot.child("Bio").getValue());
                    String file_identifier = (String) (messageSnapshot.child("file name").getValue());
                    userName.setText(username);
                    bio.setText(BIO);
                    StorageReference pathReference = storage.child("Profile photos/"+file_identifier);
                    // Load the image using Glide
                    Glide.with(ProfileFragment.this)
                            .using(new FirebaseImageLoader())
                            .load(pathReference)
                            .into(profileView);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        myRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()){

                    String poem_name = (String) (messageSnapshot.child("Title").getValue());

                    //userName.setText(poem_name);
                    userPoems.add(poem_name);
                    arrayAdapter.notifyDataSetChanged();



                //System.out.println("Title: " + newPost.get("title"));

            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        Bitmap image=((BitmapDrawable)profileView.getDrawable()).getBitmap();
        roundedImage = new RoundImage(image);
        profileView.setImageDrawable(roundedImage);

        return myView;
        }}
