package com.example.aksha.gjusteve.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Objects;

public class ConnectionCheck{

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo networkInfo = null;

        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        } catch (Exception e) {
            Log.e("connectivity", e.toString());
        }

        return networkInfo != null && networkInfo.isConnected();

    }

}
