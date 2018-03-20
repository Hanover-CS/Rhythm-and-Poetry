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
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
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

    private EditText userName, bio, file_name;
    private Button updateButton, submitButton;
    private static int GET_FROM_GALLERY = 1;
    final int REQUEST_CODE=1;
    //private DatabaseReference mDatabase;
    LinkedHashMap<String, String> userData = new LinkedHashMap<String, String>();

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String key = currentFirebaseUser.getUid().toString();

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("User").child(key);

    private ImageView profile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile);
        storage= FirebaseStorage.getInstance().getReference();
        profile = (ImageView) findViewById(R.id.previewPic);
        progress = new ProgressDialog(this);
        Button updateButton = (Button) findViewById(R.id.update);
        Button submitButton = (Button) findViewById(R.id.submit);
        userName = (EditText) findViewById(R.id.user_name);
        bio = (EditText) findViewById(R.id.bio);
        file_name = (EditText) findViewById(R.id.fileName);
        storage = FirebaseStorage.getInstance().getReference();


        //mDatabase = FirebaseDatabase.getInstance().getReference();
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                if(!file_name.getText().toString().isEmpty()) {
                    intent.setAction(Intent.ACTION_PICK);

                    intent.setType("image/*");

                    startActivityForResult(intent, REQUEST_CODE);
                }
                else if(file_name.getText().toString().isEmpty()){
                    file_name.setError("FIELD CANNOT BE EMPTY");
                }
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

                if(file_name.getText().toString().isEmpty()){
                    file_name.requestFocus();
                    file_name.setError("FIELD CANNOT BE EMPTY");
                    return;
                }

                userData.put("User name",userName.getText().toString());
                userData.put("Bio",bio.getText().toString());
                userData.put("file name", file_name.getText().toString());



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

        if(requestCode==REQUEST_CODE && resultCode == RESULT_OK)

        {
            progress.setMessage("uploading");
            progress.show();
            Uri uri=data.getData();
            StorageReference path = storage.child("Profile photos").child(file_name.getText().toString());
            path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(updateProfile.this).load(downloadUri).into(profile);

                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"Sucessfully uploaded",Toast.LENGTH_LONG).show();
                }
            });
}};}
