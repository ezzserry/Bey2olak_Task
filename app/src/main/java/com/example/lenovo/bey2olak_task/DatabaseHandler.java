package com.example.lenovo.bey2olak_task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 25/02/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "POIs_manager";

    // POIs table name
    private static final String Table_POIs = "POIs";

    // POIs Table Columns names
    private static final String ID = "id";
    private static final String Name = "name";
    private static final String Address = "address";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POIs_TABLE = "CREATE TABLE " + Table_POIs
                + "(" + ID + " INTEGER,"
                + Name + " TEXT,"
                + Address + " TEXT" + ")";
        db.execSQL(CREATE_POIs_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Table_POIs);

        // Create tables again
        onCreate(db);
    }

    // Adding new poi
    void AddPOI(POI_Data poiData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, poiData.getId()); //  Id
        values.put(Name, poiData.getName()); // Name
        values.put(Address, poiData.getAddress()); // Address

        // Inserting Row
        db.insert(Table_POIs, null, values);
        db.close(); // Closing database connection
    }

    // Getting All POIS
    public List<POI_Data> getAllPOIS() {
        List<POI_Data> contactList = new ArrayList<POI_Data>();
        // Select All Query
        String selectQuery = "SELECT*FROM " + Table_POIs;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                POI_Data project = new POI_Data();
                project.setId(Integer.parseInt(cursor.getString(0)));
                project.setName(cursor.getString(1));
                contactList.add(project);
                cursor.moveToNext();
            }
            // looping through all rows and adding to list
        }

        return contactList;
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + Table_POIs;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
