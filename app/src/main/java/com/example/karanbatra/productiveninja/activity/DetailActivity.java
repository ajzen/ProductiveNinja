package com.example.karanbatra.productiveninja.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ImageView finalimage;
    EditText max_sec;
    Button savebutton;
    Spinner spinner;
    String category;
    final DBHelper db = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*(0.6)),(int)(height*.6));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final List<String> categories = new ArrayList<>();
        categories.add("Social");
        categories.add("Games");
        categories.add("Media");
        categories.add("Communication");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner=(Spinner)findViewById(R.id.category_list_spinner);
        spinner.setAdapter(dataAdapter);

        TextView textView = (TextView)findViewById(R.id.name);
        final Intent intent = getIntent();

        textView.setText(intent.getStringExtra(intent.EXTRA_TEXT));
       final List<Contact> contacts = db.getAllContacts();
        savebutton=(Button)findViewById(R.id.savebutton);
        finalimage=(ImageView)findViewById(R.id.finalimage);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        finalimage.setImageBitmap(bmp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
              Log.e("p is ", "" + p);
              if (p == 0) {
                  category = "Social";
              } else if (p == 1) {
                  category = "Games";
              } else if (p == 2) {
                  category = "Media";
              } else if (p == 3) {
                  category = "Communication";
              } else {
                  category = "Other";
              }
          }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max_sec=(EditText)findViewById(R.id.maxSeconds);
                db.addContact(new Contact(intent.getStringExtra("packagename"), 0, 0, 0, category,Integer.parseInt(max_sec.getText().toString())));

            }
        });
    }
}



