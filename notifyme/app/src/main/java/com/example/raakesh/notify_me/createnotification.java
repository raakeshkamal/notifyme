package com.example.raakesh.notify_me;

import android.app.Application;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by raakesh on 15/1/15.
 */
public class createnotification extends Application {
    String  APPLICATION_ID="b0tUyz02ZJefQhhCFBD4Fo8yvlz2iOvMO0qQilPJ";
    String CLIENT_KEY="1Erpwgd8zTsw2j3X03ZHdwTWAnobPnnG0E8oOjRa";
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
