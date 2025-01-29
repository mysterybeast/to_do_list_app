package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Task
import com.example.todolist.repository.TaskRepository

class TaskListViewModel : ViewModel() {

    private val taskDao = App.instance.db.taskDao()
    private val repository = TaskRepository(taskDao)

    private val _taskItemListLiveData = MutableLiveData<List<Task>>()
    val taskItemListLiveData: LiveData<List<Task>> = _taskItemListLiveData

    private val taskListLiveData = repository.getAllTasks
    private val taskListObserver = Observer<List<Task>> {
        _taskItemListLiveData.postValue(taskListLiveData.value ?: emptyList())
    }

    init {
        taskListLiveData.observeForever(taskListObserver)
    }
}