package com.rap.rhythmandpoetry;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.rap.rhythmandpoetry.updateProfile;

import static android.content.ContentValues.TAG;

public class FacebookLogin extends Activity
{
    private TextView tvfirst_name, tvlast_namee, tvfull_name, tvEmail;
    private CallbackManager callbackManager;
    LoginButton login_button;
    String email,name,first_name,last_name;
    private TextView info;
    private FirebaseAuth mAuth;
    private static updateProfile userInfo = new updateProfile();


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.facebook_login);
        info = (TextView)findViewById(R.id.info);
        tvfirst_name        = (TextView) findViewById(R.id.first_name);
        tvlast_namee        = (TextView) findViewById(R.id.last_name);
        tvfull_name         = (TextView) findViewById(R.id.full_name);
        tvEmail             = (TextView) findViewById(R.id.email);
        login_button        = (LoginButton) findViewById(R.id.login_button);

        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                login_button.setVisibility(View.GONE);
                handleFacebookAccessToken(loginResult.getAccessToken());

                GraphRequest graphRequest   =   GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        Log.d("JSON", ""+response.getJSONObject().toString());

                        try
                        {
                            email       =   object.getString("email");
                            name        =   object.getString("name");
                            first_name  =   object.optString("first_name");
                            last_name   =   object.optString("last_name");

                            tvEmail.setText(email);
                            tvfirst_name.setText(first_name);
                            tvlast_namee.setText(last_name);
                            tvfull_name.setText(name);
                            LoginManager.getInstance().logOut();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,first_name,last_name,email");
                    graphRequest.setParameters(parameters);
                    graphRequest.executeAsync();

                    Intent i = new Intent(FacebookLogin.this, RapRoot.class);
                    startActivity(i);
            }


            @Override
            public void onCancel()
            {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException exception)
            {
                info.setText("Login attempt failed.");
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Iterator<String> myVeryOwnIterator = userInfo.userData.keySet().iterator();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                Intent signUp = new Intent(FacebookLogin.this, updateProfile.class);
                                startActivity(signUp);
                            }
//                            User profile_info = new User(userInfo.getElementByIndex(userInfo.userData,0).toString(),
//                                    userInfo.getElementByIndex(userInfo.userData,1).toString(),
//                                    userInfo.getElementByIndex(userInfo.userData,2).toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FacebookLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}