package com.example.karanbatra.productiveninja.activity;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class AppService extends Service {
    Handler mHandler;
    Runnable mRunnable;
    long millis;
    long millisend;
    Button b;
    EditText mess;

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

        mHandler=new Handler();
        mRunnable=new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(mRunnable,1000);
                getTopActivtyFromLolipopOnwards();
            }
        };
        mRunnable.run();



        return super.onStartCommand(intent, flags, startId);
    }


    public void getTopActivtyFromLolipopOnwards(){
        String topPackageName ;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time -Integer.MAX_VALUE, time);
            // Sort the stats by the last time used
            if(stats != null) {
                SortedMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
//                    Log.e(""+usageStats.getLastTimeUsed(), ""+usageStats);
                }
                if(mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    List<Contact> contacts = db.getAllContacts();
                    int flag=0;
                    for (Contact cn : contacts) {
                        String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Seconds: " +
                        cn.getSeconds()+ ", Minutes: " + cn.getMinutes() + ", Hours: " + cn.getHours() + "\n";
                        if(cn.getName().equals(topPackageName.toString()))
                        {
                            flag=1;
                            if(cn.getSeconds()==59){
                                if(cn.getMinutes()==59)
                                    db.updateContact(new Contact(cn.getID(),topPackageName.toString(),0,0,  cn.getHours()+1));
                                else
                                    db.updateContact(new Contact(cn.getID(),topPackageName.toString(),0,cn.getMinutes()+1,  cn.getHours()));
                            }else
                                db.updateContact(new Contact(cn.getID(),topPackageName.toString(),cn.getSeconds()+1, cn.getMinutes(), cn.getHours()));
                        }
//                        Log.e("database",log);
                    }
                    if(flag==0)
                        db.addContact(new Contact(topPackageName.toString(),0, 0,0));

                }else{
                    topPackageName=null;
                }
            }
        }

    }
}
