package com.example.skheberi.cs571app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by SKheberi on 4/16/17.
 */

public class FacebookActivity  extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    static boolean trial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        setContentView(R.layout.facebook_activity);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
             //   Toast.makeText(FacebookActivity.this, "This is my Toast message!",
               //         Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }});

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(CustomAdapter.selName)
                    .setContentDescription(
                            "FB SEARCH FROM USC CSCI571")
                    .setImageUrl(Uri.parse(CustomAdapter.selURL))
                    .setContentUrl(Uri.parse("http://cs-server.usc.edu:45678/"))
                    .build();

            shareDialog.show(linkContent);
           // MessageDialog.show(FacebookActivity.this,linkContent);
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
