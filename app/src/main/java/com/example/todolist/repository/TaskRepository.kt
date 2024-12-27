package com.example.todolist.repository

import androidx.lifecycle.LiveData
import com.example.todolist.data.TaskDao
import com.example.todolist.model.Task


class TaskRepository(private val taskDao: TaskDao) {

    val getAllTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun addTask(task: Task) = taskDao.addTask(task)
    suspend fun getTask(id: Int) = taskDao.getTask(id)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}