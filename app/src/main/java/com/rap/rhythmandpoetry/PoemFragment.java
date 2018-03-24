package com.rap.rhythmandpoetry;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    DatabaseReference myRef = mDatabase.getReference("User Poems");

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String key = currentFirebaseUser.getUid().toString();
    DatabaseReference myRef2 = mDatabase.getReference("User Poems").child(key);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.poem_layout, container, false);
        Button save = (Button) myView.findViewById(R.id.save);
        Button savePhone = (Button) myView.findViewById(R.id.phone);
        final EditText messageView = (EditText)myView.findViewById(R.id.poem);
        final EditText titleView = (EditText)myView.findViewById(R.id.title);


        /*
        When Save button is clicked all data is stored into firebase database under appropriate sections
        * */
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get the poem the user has typed
                String messageText = messageView.getText().toString();

                // get the title of the poem user chose
                String titleText = titleView.getText().toString();

                // put these values into Hash map to be stored in firebase database
                userData.put("Title",titleText);
                userData.put("Poem",messageText);

                // listen for changes to data at the location where Poem info is stored
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // set the value at location to the values passed through User data ( all info contained in the poem Fragment)
                myRef.child(key).child(titleText).setValue(userData);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new ProfileFragment())
                        .commit();
            }
        });


        /*
        Handles the option of saving the poem locally on the user's device by clicking save to phone
        * */
        savePhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText messageView = (EditText)myView.findViewById(R.id.poem);
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

