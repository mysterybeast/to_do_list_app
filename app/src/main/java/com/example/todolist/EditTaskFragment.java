package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.todolist.Data.DataBaseHandler;
import com.example.todolist.Model.Task;


public class EditTaskFragment extends Fragment {

    private EditText header, description;
    private ImageButton backImageButton;
    private DataBaseHandler dataBaseHandler;
    private Task task;
    private int taskId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        dataBaseHandler = new DataBaseHandler(getActivity());

        header = view.findViewById(R.id.header);
        description = view.findViewById(R.id.description);
        backImageButton = view.findViewById(R.id.backImageButton);

        Bundle bundle = getArguments();
        if (bundle != null) taskId = bundle.getInt("id");

        if (taskId > 0) {
            task = dataBaseHandler.getTask(taskId);
            header.setText(task.getHeader());
            description.setText(task.getDescription());
        }

        backImageButton.setOnClickListener(v -> {
            if (taskId > 0 && isFieldsChanged() & isFieldsNotEmpty()) {
                task = new Task(taskId, header.getText().toString(),
                        description.getText().toString());
                dataBaseHandler.updateTask(task);
            } else if (taskId == 0 & isFieldsNotEmpty()) {
                task = new Task(header.getText().toString(),
                        description.getText().toString());
                dataBaseHandler.addTask(task);
            }
            getParentFragmentManager().popBackStack();
        });
        return view;
    }

    boolean isFieldsNotEmpty() {
        return (!header.getText().toString().isBlank() &
                !description.getText().toString().isBlank());
    }

    boolean isFieldsChanged() {
        return !(header.getText().toString().equals(task.getHeader())) |
                !(description.getText().toString().equals(task.getDescription()));
    }
}