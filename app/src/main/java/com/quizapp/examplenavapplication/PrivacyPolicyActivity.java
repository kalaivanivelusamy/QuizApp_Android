package com.quizapp.examplenavapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Log.d("Privacy policy","privacy");
        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("file:///android_asset/privacy_policy.html");

    }

}
