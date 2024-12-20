package com.example.todolist.Data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.Model.Task

class DataBaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_HEADER + " TEXT, "
                + KEY_DESCRIPTION + " TEXT"
                + " )")

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTask(task: Task) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_HEADER, task.header)
        contentValues.put(KEY_DESCRIPTION, task.description)

        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getTask(id: Int): Task {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, arrayOf(KEY_ID, KEY_HEADER, KEY_DESCRIPTION), KEY_ID + "=?",
            arrayOf(id.toString()), null, null, null, null
        )

        cursor.moveToFirst()
        return Task(cursor.getString(0).toInt(), cursor.getString(1), cursor.getString(2))
    }

    @SuppressLint("Recycle")
    fun getAllTasks(): ArrayList<Task> {
        val db = this.readableDatabase
        val tasks = java.util.ArrayList<Task>()
        val selectAllTasks = "Select * from $TABLE_NAME"

        val cursor = db.rawQuery(selectAllTasks, null)

        if (cursor.moveToFirst()) {
            do {
                val task = Task()
                task.id = cursor.getString(0).toInt()
                task.header = cursor.getString(1)
                task.description = cursor.getString(2)

                tasks.add(task)
            } while (cursor.moveToNext())
        }
        return tasks
    }

    fun updateTask(task: Task): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_HEADER, task.header)
        contentValues.put(KEY_DESCRIPTION, task.description)

        return db.update(
            TABLE_NAME, contentValues, "$KEY_ID=?", arrayOf(task.id.toString())
        )
    }

    fun deleteTask(task: Task) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(task.id.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION: Int = 1
        private const val DATABASE_NAME: String = "tasksDB"
        private const val TABLE_NAME: String = "tasks"
        private const val KEY_ID: String = "id"
        private const val KEY_HEADER: String = "header"
        private const val KEY_DESCRIPTION: String = "description"
    }
}