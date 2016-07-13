package com.example.karanbatra.productiveninja.activity;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.karanbatra.productiveninja.R;


public class AppService extends Service {
    Handler mHandler;
    Runnable mRunnable;
    long millis;

    DBHelper db = new DBHelper(this);

    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        millis = System.currentTimeMillis() / 1000;
//        try {
//            PackageManager packageManager = this.getPackageManager();
//            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.getPackageName(), 0);
//            AppOpsManager appOpsManager = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
//            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
//            if (mode == AppOpsManager.MODE_ALLOWED)
//                showNotification();
//            else
//                cancelNotification(0);
//        Log.e("------",applicationInfo.packageName);
//
//        } catch (PackageManager.NameNotFoundException e) {
//          cancelNotification(0);
//            Log.e("","here");
//        }
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(mRunnable, 1000);

                getTopActivtyFromLolipopOnwards();
            }
        };
        mRunnable.run();
        return Service.START_STICKY;
    }


    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(AppService.this, AppService.class);
        PendingIntent pIntent = PendingIntent.getActivity(AppService.this, 0, intent, 0);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("App service running!")
                .setContentText("Here's an awesome update for you!")
                .setSmallIcon(R.drawable.ninja)
                .setContentIntent(pIntent)
                .setSound(soundUri)


                .addAction(R.drawable.ninja, "View", pIntent)
                .addAction(0, "Remind", pIntent)

                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }

    public void cancelNotification(int notificationId){

        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
    }

    public void getTopActivtyFromLolipopOnwards() {
        String topPackageName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - Integer.MAX_VALUE, time);
            // Sort the stats by the last time used
            if (stats != null) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();

                    List<Contact> contacts = db.getAllContacts();
                    for(Contact cn : contacts) {
                        if(topPackageName.equals(cn.getName()) && cn.getSeconds() >= cn.getMax_sec()) {
                            Toast.makeText(AppService.this, "You have exceeded the max time for this app \nForce closing app", Toast.LENGTH_SHORT).show();
                            Intent startHomescreen=new Intent(Intent.ACTION_MAIN);
                            startHomescreen.addCategory(Intent.CATEGORY_HOME);
                            startHomescreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startHomescreen);
                            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(800);
                        }
                    }
                    List<Contact> contacts1 = db.getAllContacts();
                    for (Contact cn : contacts1) {
                        Log.e(topPackageName.toString(),cn.getName());
                        if (cn.getName().equals(topPackageName.toString())) {
                            Log.e(topPackageName.toString(),cn.getName());
                            if (cn.getSeconds() == 59) {
                                if (cn.getMinutes() == 59)
                                    db.updateContact(new Contact(cn.getID(), topPackageName.toString(), 0, 0, cn.getHours() + 1, cn.getCategory(),cn.getMax_sec()));
                                else
                                    db.updateContact(new Contact(cn.getID(), topPackageName.toString(), 0, cn.getMinutes() + 1, cn.getHours(), cn.getCategory(),cn.getMax_sec()));
                            } else
                                db.updateContact(new Contact(cn.getID(), topPackageName.toString(), cn.getSeconds() + 1, cn.getMinutes(), cn.getHours(), cn.getCategory(),cn.getMax_sec()));
                        }
                        Log.e("details","name"+cn.getName()+"time"+cn.getSeconds()+"max"+cn.getMax_sec());
                    }
                } else {
                    topPackageName = null;
                }
            }
        }
    }
}


