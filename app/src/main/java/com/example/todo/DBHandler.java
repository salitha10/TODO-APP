package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    //DB details
    private static final int VERSION = 1; //New version
    private static final String DB_NAME = "todoDB";
    private static final String TABLE_NAME = "todo";

    //Columns
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description" ;
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    //Constructor
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //DB create query
        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " + 
                "("
                +ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + "TEXT,"
                +DESCRIPTION + "TEXT,"
                +STARTED + " TEXT,"
                +FINISHED + " TEXT" + 
                ")";

        //Run query
        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Query to drop table
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

        //Drop table
        db.execSQL(DROP_TABLE_QUERY);

        //Create new table
        onCreate(db);
    }
}
