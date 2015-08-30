package com.co.colector.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.co.colector.ColectorApplication;

/**
 * Created by User on 30/08/2015.
 */
public class ConnectionManager {

    @Nullable
    public static Boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) ColectorApplication.getInstance().
                                        getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
