package com.example.a2_2081_dev_bhatt;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class EventGoogleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        // using the ID set in previous step, get reference to the WebView
        WebView webView = findViewById(R.id.webView);

        // get country name from Intent
        String countryName = getIntent().getExtras().getString("countryName");


        // compile the Wikipedia URL, which will be used to load into WebView
        String wikiPageURL = "https://www.google.com/search?q=" + countryName;

        // set new WebView Client for the WebView
        // This gives the WebView ability to be load the URL in the current WebView
        // instead of navigating to default web browser of the device
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(wikiPageURL);



    };




}
