package com.example.todolist.Model

data class Task(val header: String, val description: String) {
    var id: Int = 0

    constructor(id: Int, header: String, description: String) : this(header, description) {
        this.id = id
    }
}