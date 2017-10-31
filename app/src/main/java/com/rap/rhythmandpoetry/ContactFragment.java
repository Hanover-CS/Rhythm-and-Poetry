package com.rap.rhythmandpoetry;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ContactFragment extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contact_layout, container, false);
        Button submitButton;
        submitButton = (Button) myView.findViewById(R.id.submit);
        final EditText txtSubject = (EditText) myView.findViewById(R.id.name);
        final EditText txtMessage = (EditText) myView.findViewById(R.id.message);
        final EditText txtNumber = (EditText) myView.findViewById(R.id.phone);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String subject = txtSubject.getText().toString();
                String message = txtMessage.getText().toString();
                String phone = txtNumber.getText().toString();
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL, new String[] {"willisr19@hanover.edu"});
//              mail.putExtra(Intent.EXTRA_SUBJECT, subject);
                mail.putExtra(Intent.EXTRA_SUBJECT, phone);
                mail.putExtra(Intent.EXTRA_TEXT, message);
//                mail.putExtra(Intent.EXTRA_CC, new String[] {"hairstonm17@hanover.edu"});
//                mail.putExtra(Intent.EXTRA_CC, new String[] {"rutledgep20@hanover.edu"});
//                mail.putExtra(Intent.EXTRA_CC, new String[] {"springerl19@hanover.edu"});
//                mail.putExtra(Intent.EXTRA_CC, new String[] {"macumberj19@hanover.edu"});
//                mail.putExtra(Intent.EXTRA_CC, new String[] {"schemedesg19@hanover.edu"});



                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Email Us using:"));


    }
});
        return myView;
}
}