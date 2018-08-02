package com.example.aksha.gjusteve.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.aksha.gjusteve.MainActivity;

import static com.example.aksha.gjusteve.helper.NotificationScheduler.showNotification;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Trigger the notification
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        String uid = bundle.getString("uid");
        showNotification(context, uid,MainActivity.class
        );
    }



}