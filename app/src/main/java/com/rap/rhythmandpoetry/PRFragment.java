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


public class PRFragment extends Fragment{

    View myView;
    RoundImage roundedImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pr, container, false);
        Button Button = (Button) myView.findViewById(R.id.pr_button);
        ImageView tynisha_image = (ImageView) myView.findViewById(R.id.tynisha);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.tynisha);
        roundedImage = new RoundImage(bm);
        tynisha_image.setImageDrawable(roundedImage);

        Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"littlet18@hanover.edu"});
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Email Tynisha Using:"));
            }
        });
        tynisha_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/tynisha.little"));
                startActivity(intent);
            }
        })
        ;
        return myView;
    }
}

