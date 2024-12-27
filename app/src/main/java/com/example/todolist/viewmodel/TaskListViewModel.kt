package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.AppDatabase
import com.example.todolist.repository.TaskRepository
import com.example.todolist.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    val getAllTasks: LiveData<List<Task>>
    private val repository: TaskRepository

    init {
        val taskDao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskRepository(taskDao)
        getAllTasks = repository.getAllTasks
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    fun getTask(id: Int): Task {
        return runBlocking {
            async(Dispatchers.IO) {
                repository.getTask(id)
            }.await()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}