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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;




import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment{

    View myView;
    LoginButton loginBtn;
    RoundImage roundedImage;
    static final String LOG_TAG = "BAD IMAGE";
    private ImageView profile;
    private ArrayList<String> userPoems = new ArrayList<>();
    String data,key2;


    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    final String key = currentFirebaseUser.getUid().toString();

    final DatabaseReference myRef= mDatabase.getReference("User").child(key).child("User Poems");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);
        final TextView user_name = (TextView) myView.findViewById(R.id.user_name);
        final TextView bio = (TextView) myView.findViewById(R.id.bio);
        ImageView profile = (ImageView) myView.findViewById(R.id.profile);

        ListView PoemsList = (ListView)myView.findViewById(R.id.poems);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, userPoems);
        PoemsList.setAdapter(arrayAdapter);

        myRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //String value = dataSnapshot.getValue(String.class);
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Poem = ds.getValue(String.class);
//                    String UserName = ds.child("User name").getValue(String.class);
//                    user_name.setText(UserName);
//                    String BIO = ds.child("Bio").getValue(String.class);
//                    bio.setText(BIO);
                    userPoems.add(Poem);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return myView;
        }}
