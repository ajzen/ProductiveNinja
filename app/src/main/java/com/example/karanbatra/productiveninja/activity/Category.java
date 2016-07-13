package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.karanbatra.productiveninja.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Category extends AppCompatActivity {
    ArrayList<CategoryListData> myList = new ArrayList<>();
    Context context = Category.this;
    DBHelper db = new DBHelper(this);
    CategoryBaseAdapter categoryBaseAdapter;
    ArrayList<String> packageNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageNames.add(packInfo.packageName);
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = packInfo.applicationInfo.loadIcon(getPackageManager());
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                CategoryListData ld = new CategoryListData();
                ld.setName(appName);
                ld.setImgBitMap(bitmap);
                myList.add(ld);
            }
        }

//        Collections.sort(myList, new CustomComparator());
        ListView listView = (ListView) findViewById(R.id.category_listView);
        categoryBaseAdapter = new CategoryBaseAdapter(context, myList);
        listView.setAdapter(categoryBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CategoryListData cld = myList.get(position);
                Intent intent = new Intent(getBaseContext(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, cld.getName());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                cld.getImgBitMap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image",byteArray);
                intent.putExtra("packagename", packageNames.get(position));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
    public class CustomComparator implements Comparator<CategoryListData> {
        @Override
        public int compare(CategoryListData o1, CategoryListData o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}
