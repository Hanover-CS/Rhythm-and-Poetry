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


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorianFragment extends Fragment {

    View myView;
    RoundImage roundedImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_historian, container, false);
        Button Button = (Button) myView.findViewById(R.id.historian_button);
        ImageView sierra = (ImageView) myView.findViewById(R.id.sierra_pic);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.sierra);
        roundedImage = new RoundImage(bm);
        sierra.setImageDrawable(roundedImage);

        Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL,new String[] {"nuckolss18@hanover.edu"});
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Email Sierra Using:"));
            }
        });
        sierra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/sierra.nuckols"));
                startActivity(intent);
            }
        })
        ;
        return myView;
    }

}
