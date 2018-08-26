package com.jskgmail.indiaskills;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandleroff extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_GRP="grp";
    private static final String KEY_MOB="mob";
    private static final String KEY_BAT="batch";

    public DatabaseHandleroff(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CITY + " TEXT," + KEY_GRP + " TEXT,"+ KEY_MOB + " TEXT,"+ KEY_BAT + " TEXT"+ ")";
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


    public void addContact(TestDetailoff contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getInstructionList()); // Contact Name
        values.put(KEY_CITY, contact.getTestDetailss_array()); // Contact Phone Number
        values.put(KEY_GRP,contact.getArrayList_3_all_questions());


        values.put(KEY_MOB,contact.getArrayList_3_all_options());
        values.put(KEY_BAT,contact.getArrayList_3_all_batch());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Getting single contact
    public TestDetailoff getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_CITY,KEY_GRP,KEY_MOB,KEY_BAT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
String k="";

        TestDetailoff contact = new TestDetailoff(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return contact
        return contact;
    }


    // Getting All Contacts
    public List<TestDetailoff> getAllContacts() {
        List<TestDetailoff> contactList = new ArrayList<TestDetailoff>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TestDetailoff contact = new TestDetailoff();
               contact.setID(Integer.parseInt(cursor.getString(0)));

               contact.setInstructionList(cursor.getString(1));
               contact.setTestDetailss_array(cursor.getString(2));
               contact.setArrayList_3_all_questions(cursor.getString(3));
                contact.setArrayList_3_all_options(cursor.getString(4));
                contact.setArrayList_3_all_batch(cursor.getString(5));

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
    public int updateContact(TestDetailoff contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

       values.put(KEY_NAME, contact.getInstructionList());
        values.put(KEY_CITY, contact.getTestDetailss_array());
        values.put(KEY_GRP,contact.getArrayList_3_all_questions());
        values.put(KEY_MOB,contact.getArrayList_3_all_options());
        values.put(KEY_BAT,contact.getArrayList_3_all_batch());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }

    // Deleting single contact
    public void deleteContact(TestDetailoff contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

}