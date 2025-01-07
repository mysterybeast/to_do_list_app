package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todolist.model.Task


@Dao
interface TaskDao {

    @Query("SElECT * FROM task_table ORDER BY id ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Delete
    suspend fun deleteTask(task: Task)

    @Upsert
    suspend fun saveTask(task: Task)
}