package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Weather_Activity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        webView = (WebView)findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl("https://weather.com/weather/today/l/23.72,90.41%22);
        //webView.loadUrl("https://www.accuweather.com/en/bd/chittagong/27822/weather-forecast/27822%22);
        //webView.loadUrl("https://www.ventusky.com/chittagong");
        //webView.loadUrl("https://www.msn.com/en-us/weather");
        webView.loadUrl("https://darksky.net/forecast/22.3424,91.8364/ca12/en");

    }
}