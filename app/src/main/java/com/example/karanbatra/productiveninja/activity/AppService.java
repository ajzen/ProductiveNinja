package com.example.karanbatra.productiveninja.activity;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
                            Toast.makeText(AppService.this, "you have exceeded max time for this app \nForce closing app", Toast.LENGTH_SHORT).show();
                            Intent startHomescreen=new Intent(Intent.ACTION_MAIN);
                            startHomescreen.addCategory(Intent.CATEGORY_HOME);
                            startHomescreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startHomescreen);
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


