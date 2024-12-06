package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.example.todolist.Data.DataBaseHandler;
import com.example.todolist.Model.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;
    RecyclerView taskList;
    DataBaseHandler dataBaseHandler;
    Button createTaskButton;
    View emptyListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // загрузка интерфейса из файла activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHandler = new DataBaseHandler(this);

        taskList = findViewById(R.id.taskList);
        emptyListLayout = findViewById(R.id.emptyListLayout);
        createTaskButton = findViewById(R.id.createTaskButton);

        TaskAdapter.OnTaskClickListener taskClickListener = (task, position) -> {
            Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);

            int idToPut = tasks.get(position).getId();
            intent.putExtra("id", idToPut);

            startActivity(intent);
        };

        setIntialData();

        taskAdapter = new TaskAdapter(this, tasks, taskClickListener);
        taskList.setAdapter(taskAdapter);
    }

    private void setIntialData() {
//        dataBaseHandler.addTask(new Task("Почитать книгу", "на свежем воздухе, желательно, лёжа в гамаке"));
//        dataBaseHandler.addTask(new Task("Сделать заготовки на зиму своими руками", "лечо, грибочки, огурцы, помидорки, варенье, кабачковая икра и т. п."));
//        dataBaseHandler.addTask(new Task("Запись в поликлинику", "28 декабря"));

        tasks = dataBaseHandler.getAllTasks();

        if (tasks.isEmpty()) emptyListLayout.setVisibility(View.VISIBLE);
        else emptyListLayout.setVisibility(View.GONE);
    }

    public void createTask(View view) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }
}