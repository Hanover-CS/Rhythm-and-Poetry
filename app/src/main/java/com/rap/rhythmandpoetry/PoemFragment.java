package com.rap.rhythmandpoetry;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedHashMap;


public class PoemFragment extends Fragment{

    View myView;
    LinkedHashMap<String, String> userData = new LinkedHashMap<String, String>();

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("User");

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String key = currentFirebaseUser.getUid().toString();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.poem_layout, container, false);
        Button Button = (Button) myView.findViewById(R.id.button);
        Button savePhone = (Button) myView.findViewById(R.id.phone);

        Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText messageView = (EditText)myView.findViewById(R.id.editText2);
                String messageText = messageView.getText().toString();
                EditText titleView = (EditText)myView.findViewById(R.id.title);
                String titleText = titleView.getText().toString();

                userData.put("Title",titleText);
                userData.put("Poem",messageText);

                String key2 = myRef.child(key).child("User Poems").push().getKey();
                myRef.child(key).child("User Poems").child(key2).setValue(userData);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new ProfileFragment())
                        .commit();
    //
            }
        });

        savePhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText messageView = (EditText)myView.findViewById(R.id.editText2);
                String messageText = messageView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, messageText);
                String chooserTitle = getString(R.string.chooser);
                Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
                startActivity(chosenIntent);

            }});
        return myView;
}
}

