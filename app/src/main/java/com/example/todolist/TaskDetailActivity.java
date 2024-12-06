package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todolist.Data.DataBaseHandler;
import com.example.todolist.Model.Task;


public class TaskDetailActivity extends AppCompatActivity {

    TextView header, description;
    DataBaseHandler dataBaseHandler;
    Task task;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHandler = new DataBaseHandler(this);

        header = findViewById(R.id.header);
        description = findViewById(R.id.description);

        header.setEllipsize(null);
        header.setSingleLine(false);
        description.setEllipsize(null);
        description.setMaxLines(20);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            task = new Task();
            id = arguments.getInt("id");
            task = dataBaseHandler.getTask(id);

            header.setText(task.getHeader());
            description.setText(task.getDescription());
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteTask(View view) {
        dataBaseHandler.deleteTask(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeTask(View view) {
        Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}