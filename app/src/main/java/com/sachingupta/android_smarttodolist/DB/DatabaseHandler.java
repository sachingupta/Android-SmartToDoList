package com.sachingupta.android_smarttodolist.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sachingupta.android_smarttodolist.ToDo.ToDo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "toDoManager";

    // Contacts table name
    private static final String TABLE_TODO = "ToDoTable";

    // To_Do Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_STATUS = "status";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_START_TIME + " TEXT," + KEY_END_TIME + " TEXT," + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);

        // Create tables again
        onCreate(db);
    }

    public void addToDo(ToDo toDo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, toDo.Id);
        values.put(KEY_TITLE, toDo.Title);
        values.put(KEY_DESCRIPTION, toDo.Description);
        values.put(KEY_CATEGORY, toDo.Category);
        values.put(KEY_START_TIME, toDo.StartTime);
        values.put(KEY_END_TIME, toDo.EndTime);
        values.put(KEY_STATUS, toDo.Status.toString());

        // Inserting Row
        db.insert(TABLE_TODO, null, values);
        db.close(); // Closing database connection
    }

    public ToDo getToDo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO, new String[] { KEY_ID, KEY_TITLE,
                        KEY_DESCRIPTION, KEY_CATEGORY, KEY_START_TIME, KEY_END_TIME, KEY_STATUS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ToDo toDo = new ToDo(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        // return todo
        return toDo;
    }

    public ArrayList<ToDo> getAllToDo() {
        ArrayList<ToDo> toDoList = new ArrayList<ToDo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                ToDo toDo = new ToDo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                toDoList.add(toDo);
            } while (cursor.moveToNext());
        }

        // return todo list
        return toDoList;
    }

    public int getToDoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count =0;
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public int nextId(){
        return this.getToDoCount() + 1;
    }

    public int updateToDo(ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, toDo.Title);
        values.put(KEY_DESCRIPTION, toDo.Description);
        values.put(KEY_CATEGORY, toDo.Category);
        values.put(KEY_START_TIME, toDo.StartTime);
        values.put(KEY_END_TIME, toDo.EndTime);
        values.put(KEY_STATUS, toDo.Status.toString());

        // updating row
        int count = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[]{String.valueOf(toDo.Id)});
        db.close(); // Closing database connection
        return count;
    }

    public void deleteToDo(ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(toDo.Id) });
        db.close();
    }
}
