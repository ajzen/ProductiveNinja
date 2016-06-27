package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    ArrayList<CategoryListData> myList = new ArrayList<>();
    Context context = Category.this;
    Button savebtn;
    DBHelper db = new DBHelper(this);
    CategoryBaseAdapter categoryBaseAdapter;

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
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = packInfo.applicationInfo.loadIcon(getPackageManager());
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                CategoryListData ld = new CategoryListData();
                ld.setName(appName);
                ld.setImgBitMap(bitmap);
                myList.add(ld);
            }
        }
        ListView listView = (ListView) findViewById(R.id.category_listView);
        categoryBaseAdapter = new CategoryBaseAdapter(context, myList);
        listView.setAdapter(categoryBaseAdapter);

        savebtn = (Button) findViewById(R.id.save_button);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer[] arr = categoryBaseAdapter.spinnerSelection();
                for(int i = 0;i < arr.length; i++){
                    Log.e("Karan", arr[i]+"");
                }
                Integer zero = new Integer(0);
                Integer one = new Integer(1);
                Integer two = new Integer(2);
                Integer three  = new Integer(3);
                List<Contact> contacts = db.getAllContacts();
                for (int i = 0; i < myList.size(); i++) {
                    String category;
                    if(arr[i].equals(zero)){
                        category="Social";
                    }else if(arr[i].equals(one)){
                        category="Games";
                    }else if(arr[i].equals(two)){
                        category="Media";
                    }else if(arr[i].equals(three)){
                        category="Communication";
                    }else{
                        category="Other";
                    }
                    CategoryListData ld = myList.get(i);
                    String appName = ld.getName();
                    int flag = 0;
                    for (Contact cn : contacts) {
                        if (cn.getName().equals(myList.get(i).getName())) {
//                            Log.e(cn.getName(), category);
                            flag = 1;
                            db.deleteContact(cn);
                            db.addContact(new Contact(appName, 0, 0, 0, category));
                        }
                    }
                    if (flag == 0)
                        db.addContact(new Contact(appName, 0, 0, 0, category));
                }
                contacts = db.getAllContacts();
                for(Contact c : contacts){
                    Log.e("KARAN", c.getName()+" "+c.getCategory());
                }
            }
        });

    }



}
