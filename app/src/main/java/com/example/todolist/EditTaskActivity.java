package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todolist.Data.DataBaseHandler;
import com.example.todolist.Model.Task;

public class EditTaskActivity extends AppCompatActivity {

    EditText header, description;
    DataBaseHandler dataBaseHandler;
    Task task;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHandler = new DataBaseHandler(this);

        header = findViewById(R.id.header);
        description = findViewById(R.id.description);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) id = arguments.getInt("id");

        if (id > 0) {
            task = new Task();
            task = dataBaseHandler.getTask(id);

            header.setText(task.getHeader());
            description.setText(task.getDescription());
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        if (id > 0) { //если открыли в режиме редактирования то переходим на TaskDetailActivity
            if (isFieldsChanged() & isFieldsNotEmpty()) { //обновляем если изменили поля
                task = new Task(id, header.getText().toString(), description.getText().toString());
                dataBaseHandler.updateTask(task);
            }

            intent = new Intent(this, TaskDetailActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);

        } else { //добавляем новую задачу
            if (isFieldsNotEmpty()) {
                task = new Task(header.getText().toString(), description.getText().toString());
                dataBaseHandler.addTask(task);
            }

            startActivity(intent);
        }
    }

    boolean isFieldsNotEmpty() {
        return (!header.getText().toString().isBlank() & !description.getText().toString().isBlank());
    }

    boolean isFieldsChanged() {
        return !(header.getText().toString().equals(task.getHeader())) |
                !(description.getText().toString().equals(task.getDescription()));
    }
}