package com.example.aksha.gjusteve.helper;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.aksha.gjusteve.R;

import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

class NotificationScheduler {

    // --Commented out by Inspection (8/2/2018 7:23 PM):public static String uid;


// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public static void setReminder(Context context,String uid, Class<?> cls, String startDate)
//    {
//        Calendar calendar = Calendar.getInstance();
//        int id = Integer.parseInt(uid);
//
//        int stDate = Integer.parseInt(startDate.substring(0,2));
//        calendar.set(Calendar.MONTH, Integer.parseInt(startDate.substring(3,5)));
//        calendar.set(Calendar.DAY_OF_MONTH, stDate);
//        calendar.set(Calendar.HOUR_OF_DAY, 8);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set( Calendar.AM_PM, Calendar.AM );
//
//        ComponentName receiver = new ComponentName(context, cls);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//
//        Intent intent1 = new Intent(context, cls);
//        intent1.putExtra("uid",id);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                id, intent1,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        assert am != null;
//        Objects.requireNonNull(am).setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
//
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public static void cancelReminder(Context context,String uid,Class<?> cls)
//    {
//        // Disable a receiver
//
//        int id  = Integer.parseInt(uid);
//        ComponentName receiver = new ComponentName(context, cls);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
//
//        Intent intent1 = new Intent(context, cls);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        assert am != null;
//        am.cancel(pendingIntent);
//        pendingIntent.cancel();
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    static void showNotification(Context context, String uid, Class<?> cls)
    {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int id = Integer.parseInt(uid);
        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("You have an upcoming event")
                .setContentText("Do not miss out")
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(id, notification);

    }

}
