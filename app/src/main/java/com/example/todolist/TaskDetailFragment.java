package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.todolist.Data.DataBaseHandler;
import com.example.todolist.Model.Task;


public class TaskDetailFragment extends Fragment {

    private TextView header, description;
    private Button changeTaskButton, deleteTaskButton;
    private ImageButton backImageButton;
    private DataBaseHandler dataBaseHandler;
    private int taskId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        dataBaseHandler = new DataBaseHandler(getActivity());

        header = view.findViewById(R.id.header);
        description = view.findViewById(R.id.description);
        changeTaskButton = view.findViewById(R.id.changeTaskButton);
        deleteTaskButton = view.findViewById(R.id.deleteTaskButton);
        backImageButton = view.findViewById(R.id.backImageButton);

        header.setEllipsize(null);
        header.setSingleLine(false);
        description.setEllipsize(null);
        description.setMaxLines(20);

        taskId = requireArguments().getInt("id");
        Task task = dataBaseHandler.getTask(taskId);
        header.setText(task.getHeader());
        description.setText(task.getDescription());

        changeTaskButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", taskId);

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.fade_out,
                            R.anim.fade_in, R.anim.slide_out)
                    .replace(R.id.fragment_container, EditTaskFragment.class, bundle)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        deleteTaskButton.setOnClickListener(v -> {
            dataBaseHandler.deleteTask(task);
            getParentFragmentManager().popBackStack();
        });

        backImageButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        return view;
    }
}