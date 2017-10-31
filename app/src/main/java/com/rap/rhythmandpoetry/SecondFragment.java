package com.rap.rhythmandpoetry;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.rap.rhythmandpoetry.R.drawable.shaun;


public class SecondFragment extends Fragment{

    View myView;
    RoundImage roundedImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        Button Button = (Button) myView.findViewById(R.id.vice_president_button);
        ImageView malik = (ImageView) myView.findViewById(R.id.malik_pic);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.malik);
        roundedImage = new RoundImage(bm);
        malik.setImageDrawable(roundedImage);

        Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL,new String[] {"Hairstonm17@hanover.edu"});
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Email Malik Using:"));
            }
        });
        malik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/search/top/?q=malik%20hairston"));
                startActivity(intent);
            }
        })
        ;
        return myView;
    }
}

