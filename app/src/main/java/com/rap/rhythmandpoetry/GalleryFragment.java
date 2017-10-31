package com.rap.rhythmandpoetry;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.provider.Contacts.SettingsColumns.KEY;
import static com.facebook.internal.FacebookRequestErrorClassification.KEY_NAME;
import static com.rap.rhythmandpoetry.R.id.image;
import static com.rap.rhythmandpoetry.R.id.name;


public class GalleryFragment extends Fragment {

    View myView;
    private ImageDatabaseHelper dBHelper;
    ImageDatabaseHelper database = new ImageDatabaseHelper(getActivity());
    String name;
    byte[] image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_gallery, container, false);

    android.widget.Button Button = (Button) myView.findViewById(R.id.upload_button);

    Button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {

            SQLiteDatabase database = dBHelper.getWritableDatabase();
               /*ContentValues cv = new  ContentValues();
                cv.put(KEY_NAME, name);
                cv.put(KEY_IMAGE, image);
                database.insert(DB_TABLE, null, cv);*/
    }
    });
        return myView;
}}