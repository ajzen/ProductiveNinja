package com.example.karanbatra.productiveninja.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;
import java.util.List;

public class SeeNotes extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<String> notes = new ArrayList<>();
    final NotesDB notesDB=new NotesDB(this);
    int pos,id;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_notes);
        final NotesDB db = new NotesDB(this);
        List<NotesFields> contacts = db.getAllContacts();
        for (NotesFields cn : contacts) {
            String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_name()   +", contents: "+cn.get_contents() + " deletevar :" + cn.get_delelte_var() + "\n";
            notes.add(cn.get_name() + "\t" + cn.get_contents() + "\n");
        }
        adapter = new ArrayAdapter<>(this, R.layout.notes_list_item, R.id.notes_item, notes);
         listView = (ListView)findViewById(R.id.notes_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name=(String)listView.getItemAtPosition(position);
                pos=position;
                alertMessage(name);
                return false;
            }
        });
    }
    public void alertMessage(final String name) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(
                    DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        listView.getChildAt(pos).setBackgroundColor(Color.RED);

                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                         NotesDB db = new NotesDB(SeeNotes.this);
                        List<NotesFields> contacts = db.getAllContacts();
                        for (NotesFields cn : contacts) {


                           if((cn.get_name()+ "\t" + cn.get_contents() + "\n").equalsIgnoreCase(name)){
                               db.deleteContact(new NotesFields(cn.get_id(),cn.get_name(),cn.get_contents(),0,0));
                               // write code here
                           }
                        }
                       // notesDB.deleteContact(notesDB.getContact(i));

                        Intent ma=new Intent(SeeNotes.this,MainActivity.class);
                        startActivity(ma);
                        // open main activity
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Try other Options") .setPositiveButton("mark as important", dialogClickListener) .setNegativeButton("delete", dialogClickListener).show(); }


}
