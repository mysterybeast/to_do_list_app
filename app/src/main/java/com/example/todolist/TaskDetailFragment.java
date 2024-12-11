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

    private TextView mHeader, mDescription;
    private Button mChangeTaskButton, mDeleteTaskButton;
    private ImageButton mBackImageButton;
    private DataBaseHandler mDataBaseHandler;
    private Task mTask;
    private int mTaskId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        mDataBaseHandler = new DataBaseHandler(getActivity());

        mHeader = view.findViewById(R.id.header);
        mDescription = view.findViewById(R.id.description);
        mChangeTaskButton = view.findViewById(R.id.changeTaskButton);
        mDeleteTaskButton = view.findViewById(R.id.deleteTaskButton);
        mBackImageButton = view.findViewById(R.id.backImageButton);

        mHeader.setEllipsize(null);
        mHeader.setSingleLine(false);
        mDescription.setEllipsize(null);
        mDescription.setMaxLines(20);

        TaskListFragment taskListFragment = new TaskListFragment();
        EditTaskFragment editTaskFragment = new EditTaskFragment();

        mChangeTaskButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", mTaskId);
            editTaskFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, editTaskFragment)
                    .addToBackStack(null)
                    .commit();
        });

        mDeleteTaskButton.setOnClickListener(v -> {
            mDataBaseHandler.deleteTask(mTask);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, taskListFragment)
                    .addToBackStack(null)
                    .commit();
        });

        mBackImageButton.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, taskListFragment)
                .addToBackStack(null)
                .commit());

        Bundle bundle = getArguments();

        if (bundle != null) {
            mTask = new Task();
            mTaskId = bundle.getInt("id");
            mTask = mDataBaseHandler.getTask(mTaskId);

            mHeader.setText(mTask.getHeader());
            mDescription.setText(mTask.getDescription());
        }

        return view;
    }
}