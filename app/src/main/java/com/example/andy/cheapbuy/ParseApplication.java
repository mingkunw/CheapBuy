package com.example.andy.cheapbuy;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by andy on 11/18/15.
 */
public class ParseApplication extends Application {

    public  static  final String YOUR_APPLICATION_ID = "zAChxIJ3Yq33iMtIK03zduxB003UV5mCJpSVwxmQ";
    public  static final String YOUR_CLIENT_KEY = "A4mZvozQuBPlnh708cr4b5Y6KgTJiQu9FHMzGLNu";

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(SellInfo.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

    }
}
