package com.example.todolist.Model;

public class Task {

    private int id;
    private String header;
    private String description;
    // private Boolean isChecked;

    public Task() {
    }

    public Task(int id, String header, String description) {
        this.id = id;
        this.header = header;
        this.description = description;
    }

    public Task(String header, String description) {
        this.header = header;
        this.description = description;
        //this.isChecked = false;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isChecked() {
        // return isChecked;
        return null;
    }

    public void setChecked(Boolean checked) {
        // isChecked = checked;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
