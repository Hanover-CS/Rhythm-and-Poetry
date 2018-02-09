package com.rap.rhythmandpoetry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rap.rhythmandpoetry.User;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rysha on 12/8/2017.
 */

public class updateProfile extends Activity {

    private EditText userName, bio, imageurl;
    private Button updateButton, submitButton;
    private static int GET_FROM_GALLERY = 1;
    //private DatabaseReference mDatabase;
    LinkedHashMap<String, String> userData = new LinkedHashMap<String, String>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user_info");




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile);
        Button updateButton = (Button) findViewById(R.id.update);
        Button submitButton = (Button) findViewById(R.id.submit);
        final EditText userName = (EditText) findViewById(R.id.user_name);
        final EditText bio = (EditText) findViewById(R.id.bio);
        final EditText imageUrl = (EditText) findViewById(R.id.imageUrl);
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
    });
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userData.put("User name",userName.toString());
                userData.put("Bio",bio.toString());
                userData.put("image", imageUrl.toString());
                String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(userData);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent i = new Intent(updateProfile.this, RapRoot.class);
                startActivity(i);
            }

        });
    }
    public Object getElementByIndex(LinkedHashMap map, int index){
        return map.get( (map.keySet().toArray())[ index ] );
    }
}
