package com.example.karanbatra.productiveninja.activity;

/**
 * Created by neeraj.varshney on 6/22/2016.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

 public class NotesDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "nrjnotepad";
    private static final String TABLE_CONTACTS = "nrjtable";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTENTS = "contents";
    private static final String KEY_DELETE_VAR = "delete_var";
    private static final String KEY_RRECYCLE_DELETE = "recycle_delete";


    public NotesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CONTENTS + " TEXT,"
                + KEY_DELETE_VAR + " INTEGER,"
                + KEY_RRECYCLE_DELETE + " INTEGER "
                 + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.e("query", CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addContact(NotesFields contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name()); // Contact Name
        values.put(KEY_CONTENTS, contact.get_contents());
        values.put(KEY_DELETE_VAR, contact.get_delelte_var());
        values.put(KEY_RRECYCLE_DELETE, contact.get_recycle_delete());
  // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    NotesFields getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME,  KEY_CONTENTS, KEY_DELETE_VAR,  KEY_RRECYCLE_DELETE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NotesFields contact = new NotesFields(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2),  cursor.getInt(3), cursor.getInt(4));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<NotesFields> getAllContacts() {
        List<NotesFields> contactList = new ArrayList<NotesFields>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotesFields contact = new NotesFields();
                contact.set_id((cursor.getInt(0)));
                contact.set_name(cursor.getString(1));
                contact.set_contents(cursor.getString(2));
                contact.set_delelte_var(cursor.getInt(3));
                contact.set_recycle_delete(cursor.getInt(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void showAll() {
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.e("set_id", "" + cursor.getInt(0));
                Log.e("set_name", "" + cursor.getString(1));
                Log.e("set_contents", "" + cursor.getString(2));
                Log.e("set_delelte_var", "" + cursor.getString(4));
                Log.e("set_contents", "" + cursor.getString(3));
                Log.e("set_recycle_delete", "" + cursor.getString(5));


            } while (cursor.moveToNext());
        }

    }

    // code to update the single contact
    public int updateContact(NotesFields contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.get_name()); // Contact Name
        values.put(KEY_CONTENTS, contact.get_contents());
        values.put(KEY_DELETE_VAR, contact.get_delelte_var());
        values.put(KEY_RRECYCLE_DELETE, contact.get_recycle_delete());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
    }

    // Deleting single contact
    public void deleteContact(NotesFields contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CONTACTS);
    }

}