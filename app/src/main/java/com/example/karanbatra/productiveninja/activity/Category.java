package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blunderer.materialdesignlibrary.activities.Activity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.listeners.OnSearchListener;

import com.blunderer.materialdesignlibrary.views.ToolbarSearch;
import com.example.karanbatra.productiveninja.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Category extends Activity {
    public static final String TAG = "Category";
    ArrayList<CategoryListData> myList = new ArrayList<>();
    Context context = Category.this;
    DBHelper db = new DBHelper(this);
    CategoryBaseAdapter categoryBaseAdapter;
    ArrayList<String> packageNames = new ArrayList<>();
    ArrayList<String> appNames = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageNames.add(packInfo.packageName);
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = packInfo.applicationInfo.loadIcon(getPackageManager());
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                CategoryListData ld = new CategoryListData();
                appNames.add(appName);
                ld.setName(appName);
                ld.setImgBitMap(bitmap);
                myList.add(ld);
            }
        }

        listView = (ListView) findViewById(R.id.category_listView);
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
    protected int getContentView() {
        return R.layout.activity_category;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarSearchHandler(this, new OnSearchListener() {

            @Override
            public void onSearched(String text) {
                ArrayList<CategoryListData> myList2 = new ArrayList<>();
                for(CategoryListData ld : myList){
                    if(ld.getName().equals(text)){
                        myList2.add(ld);
                    }
                }
                categoryBaseAdapter = new CategoryBaseAdapter(context, myList2);
                listView.setAdapter(categoryBaseAdapter);
            }

        }).enableAutoCompletion().setAutoCompletionMode(ToolbarSearch.AutoCompletionMode.STARTS_WITH)
        .setAutoCompletionSuggestions(appNames);
    }
}
