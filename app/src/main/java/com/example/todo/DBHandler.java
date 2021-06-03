package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    //DB details
    private static final int VERSION = 1; //New version
    private static final String DB_NAME = "todoDB";
    private static final String TABLE_NAME = "todo";

    //Columns
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    //Constructor
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //DB create query
        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " +
                "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + STARTED + " TEXT,"
                + FINISHED + " TEXT" +
                ")";

        //Run query
        db.execSQL(TABLE_CREATE_QUERY);
        Log.d("DB CREATE", "DB Created");

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

    //Pass data to db
    public void addToDo(ToDoModel todo) {

        //Write
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //Structure date
        ContentValues contentValues = new ContentValues();


        //Insert data
        contentValues.put(TITLE, todo.getTitle());
        contentValues.put(DESCRIPTION, todo.getDescription());
        contentValues.put(STARTED, todo.getStarted());
        contentValues.put(FINISHED, todo.getFinished());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        //Close connection
        sqLiteDatabase.close();

    }


    //Count record count
    public int getCountTODO() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        //Read data
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //Return count
        return cursor.getCount();
    }


    //Get all records
    public List<ToDoModel> getAllToDos() {

        List<ToDoModel> toDos = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        //Get data
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //Move to row
        if (cursor.moveToFirst()) {
            do {
                //object
                ToDoModel toDo = new ToDoModel();

                //Set data in model
                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDescription(cursor.getString(2));
                toDo.setStarted(cursor.getLong(3));
                toDo.setFinished(cursor.getLong(4));

                //Add to list
                toDos.add(toDo);

            } while (cursor.moveToNext());
        }
        return toDos;
    }

    //Delete item
    public void deleteItem(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //String query = "DELETE FROM " + TABLE_NAME + "WHERE id = " + id;
        sqLiteDatabase.delete(TABLE_NAME, "id " + "= ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    //Update data
    public void updateItem(ToDoModel todo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Insert data
        contentValues.put(TITLE, todo.getTitle());
        contentValues.put(DESCRIPTION, todo.getDescription());
        contentValues.put(STARTED, todo.getStarted());
        contentValues.put(FINISHED, todo.getFinished());

        //Update
        sqLiteDatabase.update(TABLE_NAME, contentValues,"id = ?", new String[]{String.valueOf(todo.getId())});
        sqLiteDatabase.close();
    }

    //Update finished status
    public void updateFinished(int id, long time){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FINISHED, time);

        //Update finished
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});
    }
}
