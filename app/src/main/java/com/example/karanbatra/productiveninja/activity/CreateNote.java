package com.example.karanbatra.productiveninja.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.karanbatra.productiveninja.R;

public class CreateNote extends AppCompatActivity {

    EditText titles,contents;
    Button saves;
    final NotesDB db = new NotesDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        saves=(Button) findViewById(R.id.savebutton);
        saves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titles=(EditText)findViewById(R.id.titless);
                final String titlestr=titles.getText().toString();
                contents=(EditText)findViewById(R.id.contentss);
                final String contentstr=contents.getText().toString();

                db.addContact(new NotesFields(titlestr,contentstr));

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
