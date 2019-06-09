package com.example.ash.twitter;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AXJsqMyKH34skn1tUROAoPKU7dAOiESM0aVmsJqh")
                // if defined
                .clientKey("odkhmDITSQjDD5JCNyuLnax1rKcqXFa8Sh04bRXz")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
