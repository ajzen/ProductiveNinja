package com.example.karanbatra.productiveninja.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.karanbatra.productiveninja.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    final DBHelper db = new DBHelper(this);
    public static final String TAG = "MainActivity";
    final int NOTIFICATION_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            addShortcut();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        try {
            PackageManager packageManager = this.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            if (mode == AppOpsManager.MODE_ALLOWED)
            {
               // showNotification();
                Notification.Builder builder = new Notification.Builder(MainActivity.this);
                builder.setContentText("App service is running").setContentTitle(
                        MainActivity.this.getString(R.string.app_name));
                builder.setSmallIcon(R.drawable.ninja);
                // make the intent object
                Intent secondActivityIntent = new Intent(MainActivity.this,
                        MainActivity.class);
                // pending intent
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                        secondActivityIntent, 0);

                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(false);
                Notification notification = builder.build();
                // this is the main thing to do to make a non removable notification
                notification.flags |= Notification.FLAG_NO_CLEAR;
                NotificationManager manager = (NotificationManager) MainActivity.this
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(NOTIFICATION_ID, notification);
            }
            else
            {
                Notification.Builder builder = new Notification.Builder(MainActivity.this);
                builder.setContentText("App service has been stopped").setContentTitle(
                        MainActivity.this.getString(R.string.app_name));
                builder.setSmallIcon(R.drawable.ninja);
                // make the intent object
                Intent secondActivityIntent = new Intent(MainActivity.this,
                        MainActivity.class);
                // pending intent
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                        secondActivityIntent, 0);

                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                // this is the main thing to do to make a non removable notification
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager manager = (NotificationManager) MainActivity.this
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(NOTIFICATION_ID, notification);
            }
            Log.e("------",applicationInfo.packageName);
                cancelNotification(0);

        } catch (PackageManager.NameNotFoundException e) {
            cancelNotification(0);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);
                startService(new Intent(this, AppService.class));
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }


            return true;
        }

        if (id == R.id.action_category) {
            startActivity(new Intent(this, Category.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new AnalyticsFragment();
                title = getString(R.string.title_analytics);
                break;
            case 1:
                title = getString(R.string.title_notes);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent=new Intent(MainActivity.this, CreateNote.class);
                                startActivity(intent);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                                Intent intents=new Intent(MainActivity.this, SeeNotes.class);
                                startActivity(intents);
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("create a new note ") .setPositiveButton("create", dialogClickListener) .setNegativeButton("see notes", dialogClickListener).show();

                break;
            case 2:  String body="";
                List<Contact> contactss = db.getAllContacts();
                for (Contact cn : contactss) {
                    body+="Name("+cn.getName()+")\t-\tTime spent("+cn.getHours()+":"+cn.getMinutes()+":"+cn.getSeconds()+")"+"\ttime limit("+cn.getMax_sec()+")\n";
                }
                generateNoteOnSD(MainActivity.this,"Phone Statistics",body);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
        }
    }
    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("App service running!")
                .setContentText("Here's an awesome update for you!")
                .setSmallIcon(R.drawable.ninja)
                .setContentIntent(pIntent)
                .setSound(soundUri)

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


    private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(),
                MainActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Productive Ninja");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.drawable.ic_launcher));

        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }
    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        File gpxfile;
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                // run your one time code
                AccountManager am = AccountManager.get(this);
                Account[] accounts = am.getAccounts();

                for (Account ac : accounts) {
                    String acname = ac.name;
                    String actype = ac.type;
                    // Take your time to look at all available accounts
                    System.out.println("Accounts : " + acname + ", " + actype);
                    if (actype.equals("com.google")) {
                        Toast.makeText(MainActivity.this, acname, Toast.LENGTH_SHORT).show();
                        SendMail sm = new SendMail(this, acname, "Productive Ninja Statistics", "We wish you will utilize this applicaion to the fullest\nfor any queries \n\t Neeraj Varshney (nvarshney97@gmail.com)",gpxfile.toString());
                        Log.e("",acname);
                        sm.execute();


                        break;

                    }
                }
            }
            else{
                Toast.makeText(MainActivity.this, "not connected to internet", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
