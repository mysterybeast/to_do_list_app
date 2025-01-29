package com.example.todolist

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.AppDatabase

class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    companion object {
        lateinit var instance: App
    }
}