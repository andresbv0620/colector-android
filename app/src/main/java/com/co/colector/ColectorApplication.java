package com.co.colector;

import android.app.Application;

/**
 * Created by User on 24/08/2015.
 */

public class ColectorApplication extends Application {

    private static ColectorApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static ColectorApplication getInstance() {
        return context;
    }
}
