package com.example.karanbatra.productiveninja.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.karanbatra.productiveninja.R;
import com.example.karanbatra.productiveninja.activity.AnalyticsFragment;
import com.example.karanbatra.productiveninja.activity.NotesDB;
import com.example.karanbatra.productiveninja.activity.NotesFields;

import java.util.List;

public class CreateNote extends AppCompatActivity {

    EditText titles,contents;
    Button saves;
    final NotesDB db = new NotesDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Log.e("---------","asfdfsa");

        saves=(Button) findViewById(R.id.savebutton);
        saves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titles=(EditText)findViewById(R.id.titless);
                final String titlestr=titles.getText().toString();
                contents=(EditText)findViewById(R.id.contentss);
                final String contentstr=contents.getText().toString();

                db.addContact(new NotesFields(titlestr,contentstr));
                List<NotesFields> contacts = db.getAllContacts();
                for (NotesFields cn : contacts) {
                    String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_name()   +", contents: "+cn.get_contents() + " deletevar :" + cn.get_delelte_var() + "\n";
                    Log.e("hhh", log);
                }
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
