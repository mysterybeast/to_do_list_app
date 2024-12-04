package com.example.todolist.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.Model.Task;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasksDB";
    public static final String TABLE_NAME = "tasks";
    public static final String KEY_ID = "id";
    public static final String KEY_HEADER = "header";
    public static final String KEY_DESCRIPTION = "description";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_HEADER + " TEXT, "
                + KEY_DESCRIPTION + " TEXT"
                + " )";

        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_HEADER, task.getHeader());
        contentValues.put(KEY_DESCRIPTION, task.getDescription());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_HEADER, KEY_DESCRIPTION},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        return new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
    }

    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        String selectAllTasks = "Select * from " + TABLE_NAME;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllTasks, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setHeader(cursor.getString(1));
                task.setDescription(cursor.getString(2));

                tasks.add(task);

            } while (cursor.moveToNext());
        }
        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_HEADER, task.getHeader());
        contentValues.put(KEY_DESCRIPTION, task.getDescription());

        return db.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();

    }
}
