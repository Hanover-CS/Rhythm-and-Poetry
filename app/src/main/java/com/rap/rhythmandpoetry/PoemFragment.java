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


public class PoemFragment extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.poem_layout, container, false);
        Button Button = (Button) myView.findViewById(R.id.button);

    Button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            EditText messageView = (EditText)myView.findViewById(R.id.editText2);
            String messageText = messageView.getText().toString();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, messageText);
            String chooserTitle = getString(R.string.chooser);
            Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
            startActivity(chosenIntent);
        }
    });
        return myView;
}
}

