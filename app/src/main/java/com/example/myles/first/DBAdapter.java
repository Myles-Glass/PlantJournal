package com.example.myles.first;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
    /*- 01 Database and table names ------------------------------ */
    private static final String databaseName        = "PlantDB";
    private static final String databaseTableNotes = "plantnotes";
    private static final int databaseVersion        = 1;


    /*- 02 Create table statements ------------------------------ */
    // Create table notes
    private static final String databaseCreateTableNotes =
            "CREATE TABLE IF NOT EXISTS " + databaseTableNotes + " " +
                    "( note_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " note_title VARCHAR," +
                    " note_text TEXT);";


    /*- 03 Database variables ----------------------------------- */
    /*- Dont edit! ---------------------------------------------- */
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    /*- 04 Class ------------------------------------------------ */
    /*- Dont edit! ---------------------------------------------- */
    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    } // end public DBAdapter


    /*- 05 Start DBAdapter -------------------------------------- */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, databaseName, null, databaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                // ! All tables that are going to be created need to be listed here
                db.execSQL(databaseCreateTableNotes);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } // end public void onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            // ! All tables that are going to be dropped need to be listed here
            db.execSQL("DROP TABLE IF EXISTS " + databaseTableNotes);
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

        } // end public void onUpgrade
    } // end private static class DatabaseHelper extends SQLiteOpenHelper

    /*- 06 Open and close database ---------------------------- */
    /*- Dont edit! -------------------------------------------- */
    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void reset() throws SQLException {
        db = DBHelper.getWritableDatabase ();
        db.execSQL ("drop table "+databaseName);
        db.close ();
        this.DBHelper.onCreate (this.db);
    }
    //---closes the database---
    public void close() {
        DBHelper.close();
    }



    /*- 07 Queries for notes ----------------------------------- */
    // Insert record
    public long insertRecordToNotes(String inpTitle, String inpText)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("note_title", inpTitle);
        initialValues.put("note_text", inpText);

        return db.insert(databaseTableNotes, null, initialValues);
    }
    // Retrieve
    public Cursor getAllRecordsFromNotes()
    {
        return db.query(databaseTableNotes, new String[]{
                "note_id",
                "note_title",
                "note_text"
        }, null, null, null, null, null, null);
    }
    public Cursor getAllRecordsFromNotesListView()
    {
        return db.query(databaseTableNotes, new String[]{
                "note_id AS _id",
                "note_title",
                "note_text"
        }, null, null, null, null, null, null);
    }
    // Retrieves a particular record
    public Cursor getRecordFromNotes(long rowId) throws SQLException {
        Cursor mCursor = db.query(databaseTableNotes, new String[] {
                        "note_id",
                        "note_title",
                        "note_text"
                },
                "note_id" + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public int truncateNotes()
    {
        return db.delete(databaseTableNotes, "1", null);
    }



    public boolean updateNote(long inpId, String inpTitle, String inpText) {
        ContentValues args = new ContentValues();
        args.put("note_title", inpTitle);
        args.put("note_text", inpText);
        return db.update(databaseTableNotes, args, "note_id=" + inpId, null) > 0;
    }
}
