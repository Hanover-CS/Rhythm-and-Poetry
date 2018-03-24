package com.rap.rhythmandpoetry;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class others extends Fragment {

    View myView;
    RecyclerView.ViewHolder holder;
    private Context context;
    private ArrayList<String> userNames = new ArrayList<>();

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    final String key = currentFirebaseUser.getUid().toString();

    DatabaseReference myRef = mDatabase.getReference("User") ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_others, container, false);
        CardView card = new CardView(new ContextThemeWrapper(getActivity(), R.style.CardView_Dark), null, 0);
        final RelativeLayout cardInner = new RelativeLayout(new ContextThemeWrapper(getActivity(), R.style.Widget_CardContent));
        cardInner.setLayoutParams(new RelativeLayout.LayoutParams(60, 60));

        ListView PoemsList = (ListView)myView.findViewById(R.id.user_names);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, userNames);
        PoemsList.setAdapter(arrayAdapter);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()){
                    String usernames = (String) (messageSnapshot.child("User name").getValue());
                    userNames.add(usernames);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return myView;
    }

}
