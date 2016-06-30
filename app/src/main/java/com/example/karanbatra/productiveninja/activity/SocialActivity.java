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
import java.util.HashSet;
import java.util.List;

public class SocialActivity extends AppCompatActivity {
    ArrayList<ListData> myList = new ArrayList<>();
    Context context = SocialActivity.this;
    HashSet<String> present = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try{
            DBHelper db = new DBHelper(this);
            List<Contact> list = db.getCategoryContacts("Social");
//            for(int i = 0;i < present.size(); i++){
//                if(!present.contains(list.get(i)))
//                        present.remove();
//            }
            for(int i = 0;i < list.size(); i++) {
                if (!present.contains(list.get(i).getName())) {
                    ApplicationInfo app = getPackageManager().getApplicationInfo(list.get(i).getName(), 0);
                    Drawable icon = getPackageManager().getApplicationIcon(app);
                    Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                    String name = getPackageManager().getApplicationLabel(app).toString();
                    ListData ld = new ListData();
                    ld.setName(name);
                    int min = list.get(i).getMinutes();
                    int seconds = list.get(i).getSeconds();
                    if (min < 10) {
                        if (seconds < 10) {
                            ld.setTime("0" + min + " : " + "0" + seconds);
                        } else {
                            ld.setTime("0" + min + " : " + seconds);
                        }
                    } else {
                        if (seconds < 10) {
                            ld.setTime(min + " : " + "0" + seconds);
                        } else {
                            ld.setTime(min + " : " + seconds);
                        }
                    }
                    ld.setImgBitMap(bitmap);
                    myList.add(ld);
                    present.add(list.get(i).getName());
                }
            }
        }
        catch(PackageManager.NameNotFoundException e){

        }
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new MyBaseAdapter(context, myList));
    }

}
