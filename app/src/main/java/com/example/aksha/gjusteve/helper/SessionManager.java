package com.example.aksha.gjusteve.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
// --Commented out by Inspection START (8/2/2018 7:23 PM):
//        // LogCat tag
//        private static String TAG = SessionManager.class.getSimpleName();
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

        // Shared Preferences
        final private SharedPreferences pref;

        final private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "GJUSTLogin";

        private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
        private static final String KEY_IS_GUEST_LOGGED_IN = "isGuestLogIn";

    public SessionManager(Context context) {
            int PRIVATE_MODE = 0;
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
            editor.apply();
        }

    public void setGuestLogin(boolean isGuestLoggedIn) {
        editor.putBoolean(KEY_IS_GUEST_LOGGED_IN, isGuestLoggedIn);
        // commit changes
        editor.commit();
    }

    public boolean isGuestLoggedIn(){
        return pref.getBoolean(KEY_IS_GUEST_LOGGED_IN, false);
    }
        public void setLogin(boolean isLoggedIn) {
            editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
            // commit changes
            editor.commit();
        }
        public void setFirstTime(boolean firstTime){
            editor.putBoolean("FIRST_TIME",firstTime);
            editor.commit();
        }
        public boolean isFirstTime(){
            return pref.getBoolean("FIRST_TIME",false);
        }

        public boolean isLoggedIn(){
            return pref.getBoolean(KEY_IS_LOGGED_IN, false);
        }
}
