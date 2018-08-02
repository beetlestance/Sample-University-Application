package com.example.aksha.gjusteve;
import android.app.Application;
import android.content.Context;

public class App extends Application {
    final public static String baseUrl = "http://192.168.0.111";
    private static Context appContext;

    public static Context getContext() {
        return appContext;
    }

    private static int glideVersion=0;
    private static int preGlideVersion = 0;

    public static int getGlideVersion() {
        return glideVersion;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public static int getPreGlideVersion() {
//        return preGlideVersion;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public static void setPreGlideVersion(int preGlideVersion) {
//        App.preGlideVersion = preGlideVersion;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public static void setGlideVersion(int glideVersion) {
        App.glideVersion = glideVersion;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
    }

}