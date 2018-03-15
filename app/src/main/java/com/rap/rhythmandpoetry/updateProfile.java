package com.rap.rhythmandpoetry;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.*;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rap.rhythmandpoetry.User;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.rap.rhythmandpoetry.ProfileFragment.LOG_TAG;

/**
 * Created by rysha on 12/8/2017.
 */

public class updateProfile extends Activity {

    StorageReference storage;
    ProgressDialog progress;

    private EditText userName, bio, imageurl;
    private Button updateButton, submitButton;
    private static int GET_FROM_GALLERY = 1;
    final int REQUEST_CODE=1;
    //private DatabaseReference mDatabase;
    LinkedHashMap<String, String> userData = new LinkedHashMap<String, String>();

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String key = currentFirebaseUser.getUid().toString();

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("User").child(key);




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile);
        storage= FirebaseStorage.getInstance().getReference();

        progress = new ProgressDialog(this);
        Button updateButton = (Button) findViewById(R.id.update);
        Button submitButton = (Button) findViewById(R.id.submit);
        final EditText userName = (EditText) findViewById(R.id.user_name);
        final EditText bio = (EditText) findViewById(R.id.bio);
        final EditText imageUrl = (EditText) findViewById(R.id.imageUrl);


        //mDatabase = FirebaseDatabase.getInstance().getReference();

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();

                intent.setAction(Intent.ACTION_PICK);

                intent.setType("image/*");

                startActivityForResult(intent,REQUEST_CODE);
                //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
    });
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(userName.getText().toString().isEmpty()){
                    userName.requestFocus();
                    userName.setError("FIELD CANNOT BE EMPTY");
                    return;
                }

                if(bio.getText().toString().isEmpty()){
                    bio.requestFocus();
                    bio.setError("FIELD CANNOT BE EMPTY");
                    return;
                }

                if(imageUrl.getText().toString().isEmpty()){
                    imageUrl.requestFocus();
                    imageUrl.setError("FIELD CANNOT BE EMPTY");
                    return;
                }

                final User user_info = new User(userName.getText().toString(), imageUrl.getText().toString(),bio.getText().toString());

                userData.put("User name",user_info.getUsername());
                userData.put("Bio",user_info.getBio());
                userData.put("image", user_info.getEmail());



                myRef.setValue(userData);
                //myRef.child(key).updateChildren(user_info);
                myRef.addValueEventListener(new ValueEventListener() {
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

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK)

        {

            progress.setMessage("uploading");
            progress.show();
            Uri uri=data.getData();
            StorageReference path=storage.child("Profile photos").child("simple_image");
            path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"Sucessfully uploaded",Toast.LENGTH_LONG).show();
                }
            });
}};}
