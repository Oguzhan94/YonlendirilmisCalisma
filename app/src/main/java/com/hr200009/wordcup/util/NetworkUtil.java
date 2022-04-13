package com.hr200009.wordcup.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

public class NetworkUtil {

    public static boolean isInternetConnected(Context getApplicationContext) {
        boolean status = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (connectivityManager.getActiveNetwork() != null && connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork()) != null) {
                    // connected to the internet
                    status = true;
                }

            } else {
                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
                    // connected to the internet
                    status = true;
                }
            }
        }
        return status;
    }
}
