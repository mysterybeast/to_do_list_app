package com.example.todolist.Model

class Task {
    var id: Int = 0
    var header: String? = null
    var description: String? = null

    constructor()

    constructor(id: Int, header: String?, description: String?) {
        this.id = id
        this.header = header
        this.description = description
    }

    constructor(header: String?, description: String?) {
        this.header = header
        this.description = description
    }
}