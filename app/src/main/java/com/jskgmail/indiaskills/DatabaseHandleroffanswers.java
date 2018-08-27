package com.jskgmail.indiaskills;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandleroffanswers extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "answersManager";
    // Contacts table name
    private static final String TABLE_CONTACTS = "answers";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_GRP="grp";
    private static final String KEY_MOB="mob";

    public DatabaseHandleroffanswers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CITY + " TEXT," + KEY_GRP + " TEXT,"+ KEY_MOB + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
}

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    public void addContact(TestDetailoffans contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getCandidateid()); // Contact Name
        values.put(KEY_CITY, contact.getTestDetailss_array()); // Contact Phone Number
        values.put(KEY_GRP,contact.getArrayList_3_all_pics());


        values.put(KEY_MOB,contact.getArrayList_3_all_options());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Getting single contact
    public TestDetailoffans getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_CITY,KEY_GRP,KEY_MOB}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
String k="";

        TestDetailoffans contact = new TestDetailoffans(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return contact
        return contact;
    }


    // Getting All Contacts
    public List<TestDetailoffans> getAllContacts() {
        List<TestDetailoffans> contactList = new ArrayList<TestDetailoffans>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TestDetailoffans contact = new TestDetailoffans();
               contact.setID(Integer.parseInt(cursor.getString(0)));

               contact.setCandidateid(cursor.getString(1));
               contact.setTestDetailss_array(cursor.getString(2));
               contact.setArrayList_3_all_questions(cursor.getString(3));
                contact.setArrayList_3_all_options(cursor.getString(4));

               contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
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


    // Updating single contact
    public int updateContact(TestDetailoffans contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

       values.put(KEY_NAME, contact.getCandidateid());
        values.put(KEY_CITY, contact.getTestDetailss_array());
        values.put(KEY_GRP,contact.getArrayList_3_all_pics());
        values.put(KEY_MOB,contact.getArrayList_3_all_options());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }

    // Deleting single contact
    public void deleteContact(TestDetailoffans contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

}