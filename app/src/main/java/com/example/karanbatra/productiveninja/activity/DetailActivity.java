package com.example.karanbatra.productiveninja.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent = getIntent();

        max_sec = (EditText) findViewById(R.id.maxSeconds);
        Contact cn = db.getContact(intent.getStringExtra("packagename"));
        if (cn != null) {
            Integer seconds = cn.getMax_sec();
            max_sec.setText(seconds.toString());
        }
        final List<String> categories = new ArrayList<>();
        categories.add("Social");
        categories.add("Games");
        categories.add("Media");
        categories.add("Communication");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_item);

        spinner=(Spinner)findViewById(R.id.category_list_spinner);
        spinner.setAdapter(dataAdapter);

        TextView textView = (TextView)findViewById(R.id.name);
        textView.setText(intent.getStringExtra(intent.EXTRA_TEXT));
        savebutton=(Button)findViewById(R.id.savebutton);
        finalimage=(ImageView)findViewById(R.id.finalimage);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        finalimage.setImageBitmap(bmp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
              if (p == 0) {
                  category = "Social";
              } else if (p == 1) {
                  category = "Games";
              } else if (p == 2) {
                  category = "Media";
              } else if (p == 3) {
                  category = "Communication";
              }
          }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max_sec = (EditText) findViewById(R.id.maxSeconds);
                int maxi = Integer.MAX_VALUE;
                Contact cn = db.getContact(intent.getStringExtra("packagename"));
                if (cn != null) {
                    db.deleteContact(cn);
                    db.addContact(new Contact(intent.getStringExtra("packagename"), cn.getSeconds(), cn.getMinutes(), cn.getHours(), category, Integer.parseInt(max_sec.getText().toString())));
                    Intent ints = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(ints);
                }
                else {
                    try{
                        db.addContact(new Contact(intent.getStringExtra("packagename"), 0, 0, 0, category, Integer.parseInt(max_sec.getText().toString())));
                        Intent ints = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(ints);
                    }catch(java.lang.NumberFormatException e) {
                        db.addContact(new Contact(intent.getStringExtra("packagename"), 0, 0, 0, category, maxi));
                        Intent ints = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(ints);
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}
