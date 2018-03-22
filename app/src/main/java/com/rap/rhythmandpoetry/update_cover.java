package com.rap.rhythmandpoetry;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.LinkedHashMap;

public class update_cover extends FragmentActivity {
    StorageReference storage;
    ProgressDialog progress;

    private EditText file_name;
    private Button updateButton2, submitButton;
    private static int GET_FROM_GALLERY = 1;
    final int REQUEST_CODE=1;

    LinkedHashMap<String, String> userData = new LinkedHashMap<String, String>();

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String key = currentFirebaseUser.getUid().toString();

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("Cover Photos").child(key);

    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cover);

        progress = new ProgressDialog(this);
        profile = (ImageView) findViewById(R.id.previewPic2);
        updateButton2 = (Button) findViewById(R.id.update2);
        submitButton = (Button) findViewById(R.id.submit);
        file_name = (EditText) findViewById(R.id.fileName2);
        storage = FirebaseStorage.getInstance().getReference();

        updateButton2.setOnClickListener(new View.OnClickListener() {
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

                if(file_name.getText().toString().isEmpty()){
                    file_name.requestFocus();
                    file_name.setError("FIELD CANNOT BE EMPTY");
                    return;
                }
                userData.put("cover file name", file_name.getText().toString());




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

                Intent i = new Intent(update_cover.this,RapRoot.class);
                startActivity(i);
            }

        });


    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode == RESULT_OK)

        {
            progress.setMessage("uploading");
            progress.show();
            Uri uri=data.getData();

            StorageReference path2 = storage.child("Cover photos").child(file_name.getText().toString());

            path2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(update_cover.this).load(downloadUri).into(profile);

                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"Sucessfully uploaded",Toast.LENGTH_LONG).show();
                }});}


        }}
