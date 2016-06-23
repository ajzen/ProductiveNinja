package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;

public class SocialActivity extends AppCompatActivity {
    ArrayList<ListData> myList = new ArrayList<ListData>();
    Context context = SocialActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try{
            ApplicationInfo app = getPackageManager().getApplicationInfo("com.whatsapp", 0);
            DBHelper db = new DBHelper(this);
            Contact c = db.getContact("com.whatsapp");
            Drawable icon = getPackageManager().getApplicationIcon(app);
            Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
            String name = getPackageManager().getApplicationLabel(app).toString();
            ListData ld = new ListData();
            ld.setName(name);
            int min = c.getMinutes();
            int seconds = c.getSeconds();
            if(min < 10){
                if(seconds < 10){
                    ld.setTime("0"+min+" : "+"0"+seconds);
                }else{
                    ld.setTime("0"+min+" : "+seconds);
                }
            }else{
                if(seconds < 10){
                    ld.setTime(min+" : "+"0"+seconds);
                }else{
                    ld.setTime(min+" : "+seconds);
                }
            }
            ld.setImgBitMap(bitmap);
            myList.add(ld);
        }
        catch(PackageManager.NameNotFoundException e){

        }
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new MyBaseAdapter(context, myList));
    }

}
