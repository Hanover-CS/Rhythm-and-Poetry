package com.rap.rhythmandpoetry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class PoemView extends Activity {

    LinkedHashMap<String, String> userData = new LinkedHashMap<String, String>();

    FirebaseDatabase mDatabase;

    FirebaseUser currentFirebaseUser;
    String key;
    DatabaseReference myRef, myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_view);

        Button saveButton = (Button)findViewById(R.id.save);
        final EditText messageView = (EditText)findViewById(R.id.poem);
        final EditText titleView = (EditText)findViewById(R.id.title);
        Button savePhone = (Button) findViewById(R.id.phone);


        mDatabase = FirebaseDatabase.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        key = currentFirebaseUser.getUid().toString();
        myRef = mDatabase.getReference("poem_name").child(key);
        myRef2 = mDatabase.getReference("User Poems").child(key);


        /*
        Get the Poem by searching database for the "value" passed from profile fragment containing the title of the poem
        * */
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String title = (String) (dataSnapshot.child("Title").getValue());
                    titleView.setText(title);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String poem = (String) (dataSnapshot.child(titleView.getText().toString()).child("Poem").getValue());
                    messageView.setText(poem);

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        When Save button is clicked all data is stored into firebase database under appropriate sections
        * */
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get the poem the user has typed
                String messageText = messageView.getText().toString();

                // get the title of the poem user chose
                String titleText = titleView.getText().toString();

                // put these values into Hash map to be stored in firebase database
                userData.put("Title",titleText);
                userData.put("Poem",messageText);

                // listen for changes to data at the location where Poem info is stored
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                // set the value at location to the values passed through User data ( all info contained in the poem Fragment)
                myRef2.child(titleText).setValue(userData);
                Intent i = new Intent(PoemView.this, RapRoot.class);
                startActivity(i);

                // close this activity
                finish();


            }
        });
        /*
        Handles the option of saving the poem locally on the user's device by clicking save to phone
        * */
        savePhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText messageView = (EditText)findViewById(R.id.poem);
                String messageText = messageView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, messageText);
                String chooserTitle = getString(R.string.chooser);
                Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
                startActivity(chosenIntent);

            }});




    }

}
