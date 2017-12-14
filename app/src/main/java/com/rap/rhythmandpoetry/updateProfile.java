package com.rap.rhythmandpoetry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by rysha on 12/8/2017.
 */

public class updateProfile extends Activity {

    private EditText userName, bio, imageurl;
    private Button updateButton;
    private static int GET_FROM_GALLERY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile);
        Button updateButton = (Button) findViewById(R.id.update);
        EditText userName = (EditText) findViewById(R.id.user_name);
        EditText bio = (EditText) findViewById(R.id.bio);
        EditText imageUrl = (EditText) findViewById(R.id.imageUrl);

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
    });}
}
