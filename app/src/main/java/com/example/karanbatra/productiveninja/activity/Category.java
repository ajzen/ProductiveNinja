package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.karanbatra.productiveninja.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    ArrayList<CategoryListData> myList = new ArrayList<CategoryListData>();
    Context context = Category.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        ArrayList<String> arr = new ArrayList<String>();
        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = packInfo.applicationInfo.loadIcon(getPackageManager());
                Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
                arr.add(appName);
                CategoryListData ld = new CategoryListData();
                ld.setName(appName);
                ld.setImgBitMap(bitmap);
                myList.add(ld);
            }
        }
        ListView listView = (ListView)findViewById(R.id.category_listView);
        listView.setAdapter(new CategoryBaseAdapter(context, myList));
    }


}
