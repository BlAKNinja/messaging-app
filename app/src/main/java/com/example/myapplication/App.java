package com.example.myapplication;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize( new Parse.Configuration.Builder(this)
        .applicationId("KBc6XBfcnkCOxyeGqc9tbqxLdCw7Uwsq24Yn0Xds")
         .clientKey("c6iOR30WiQ3TFfiBpOZU0gF3vCsGQyKHy8THz6VA")
                .server("https://parseapi.back4app.com/")
                .build()

        );
        ParseACL defaultacl=new ParseACL();
        defaultacl.setPublicReadAccess(true);
        defaultacl.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultacl,true);
    }
}
