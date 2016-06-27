package com.example.karanbatra.productiveninja.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;
import java.util.List;

public class SeeNotes extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<String> notes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_notes);
        final NotesDB db = new NotesDB(this);
        List<NotesFields> contacts = db.getAllContacts();
        for (NotesFields cn : contacts) {
            String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_name()   +", contents: "+cn.get_contents() + " deletevar :" + cn.get_delelte_var() + "\n";
            notes.add(cn.get_name() + "\n" + cn.get_contents() + "\n");
        }
        adapter = new ArrayAdapter<>(this, R.layout.notes_list_item, R.id.notes_item, notes);
        ListView listView = (ListView)findViewById(R.id.notes_list_view);
        listView.setAdapter(adapter);
    }
}
