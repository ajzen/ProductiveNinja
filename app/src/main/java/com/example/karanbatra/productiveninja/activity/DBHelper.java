package com.example.karanbatra.productiveninja.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "asdf";
    private static final String TABLE = "tree";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SECONDS="seconds";
    private static final String KEY_MINUTES="minutes";
    private static final String KEY_HOURS="hours";
    private static final String KEY_CATEGORY="category";
    private static final String KEY_MAX="max";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_NAME + " TEXT,"
                + KEY_SECONDS + " INTEGER ," + KEY_MINUTES + " INTEGER , " + KEY_HOURS+ " INTEGER , "+ KEY_CATEGORY+" TEXT ,"+KEY_MAX+ " INTEGER"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_SECONDS, contact.getSeconds()); // Contact Phone
        values.put(KEY_MINUTES, contact.getMinutes());
        values.put(KEY_HOURS, contact.getHours());
        values.put(KEY_CATEGORY, contact.getCategory());
        values.put(KEY_MAX,contact.getMax_sec());
        // Inserting Row
        db.insert(TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all contacts in a list view
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setSeconds(cursor.getInt(2));
                contact.setMinutes(cursor.getInt(3));
                contact.setHours(cursor.getInt(4));
                contact.setCategory(cursor.getString(5));
                contact.setMax_sec(cursor.getInt(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_SECONDS, contact.getSeconds());
        values.put(KEY_MINUTES, contact.getMinutes());
        values.put(KEY_HOURS, contact.getHours());
        values.put(KEY_CATEGORY, contact.getCategory());
        values.put(KEY_MAX,contact.getMax_sec());
        // updating row
        return db.update(TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    public List<Contact> getCategoryContacts(String category) {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(5).equals(category)) {
                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setSeconds(cursor.getInt(2));
                    contact.setMinutes(cursor.getInt(3));
                    contact.setHours(cursor.getInt(4));
                    contact.setCategory(cursor.getString(5));
                    contact.setMax_sec(cursor.getInt(6));
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }
        return contactList;
    }


}