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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment{

    View myView;
    View myView1;
    TextView userName;
    LoginButton loginBtn;
    RoundImage roundedImage;
    static final String LOG_TAG = "BAD IMAGE";
    private DatabaseReference mDatabase;
    private EditText user_name, bio;
    private Button save;
    private ImageView profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //save = (Button) myView.findViewById(R.id.save);
        user_name = (EditText) myView.findViewById(R.id.user_name);
        bio = (EditText) myView.findViewById(R.id.bio);
        profile = (ImageView) myView.findViewById(R.id.profile);
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("profile_data");
        DatabaseReference message = mDatabase.child("profile_data");
        mDatabase.push().getKey();
//        FirebaseDatabase usersRef = ref.child("Users");
//        Map<String, String> userData = new HashMap<String, String>();



        save.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                String user_nameText = "";

                String bioText = "";

                String urlText = "";

                if (user_name.getText().toString() != null) {

                    user_nameText = user_name.getText().toString();

                    //message.child();
                }

                if (bio.getText().toString() != null) {

                    bioText = bio.getText().toString();
                }

                final String finaluser_nameText = user_nameText;
                final String finalbioText = bioText;
            }


        });










//        if (AccessToken.getCurrentAccessToken() != null) {
//
//            GraphRequest request = GraphRequest.newMeRequest(
//                    AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject me, GraphResponse response) {
//
//                            if (AccessToken.getCurrentAccessToken() != null) {
//
//                                if (me != null) {
//
//                                    String profileImageUrl = ImageRequest.getProfilePictureUri(me.optString("id"), 500, 500).toString();
//                                    Log.i(LOG_TAG, profileImageUrl);
//
//                                }
//                            }
//                        }
//                    });
//            GraphRequest.executeBatchAsync(request);
//        }
        return myView;
    };
        
        /*Button Button = (Button) myView.findViewById(R.id.president_button);
        ImageView shaun = (ImageView) myView.findViewById(R.id.shaun_pic);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.shaun);
        roundedImage = new RoundImage(bm);
        shaun.setImageDrawable(roundedImage);

        ChoosePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                try {
                    if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

