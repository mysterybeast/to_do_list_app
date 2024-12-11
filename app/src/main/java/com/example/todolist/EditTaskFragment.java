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

    private EditText mHeader, mDescription;
    private ImageButton mBackImageButton;
    private DataBaseHandler mDataBaseHandler;
    private Task mTask;
    private int mTaskId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        mDataBaseHandler = new DataBaseHandler(getActivity());

        mHeader = view.findViewById(R.id.header);
        mDescription = view.findViewById(R.id.description);
        mBackImageButton = view.findViewById(R.id.backImageButton);

        Bundle bundle = getArguments();

        if (bundle != null) mTaskId = bundle.getInt("id");

        if (mTaskId > 0) {
            mTask = new Task();
            mTask = mDataBaseHandler.getTask(mTaskId);
            mHeader.setText(mTask.getHeader());
            mDescription.setText(mTask.getDescription());
        }

        mBackImageButton.setOnClickListener(v -> {
            TaskListFragment taskListFragment = new TaskListFragment();
            TaskDetailFragment taskDetailFragment = new TaskDetailFragment();

            if (mTaskId > 0) {
                if (isFieldsChanged() & isFieldsNotEmpty()) { //обновляем если изменили поля
                    mTask = new Task(mTaskId, mHeader.getText().toString(),
                            mDescription.getText().toString());
                    mDataBaseHandler.updateTask(mTask);
                }

                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", mTaskId);
                taskDetailFragment.setArguments(bundle1);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, taskDetailFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                if (isFieldsNotEmpty()) {
                    mTask = new Task(mHeader.getText().toString(),
                            mDescription.getText().toString());
                    mDataBaseHandler.addTask(mTask);
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, taskListFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    boolean isFieldsNotEmpty() {
        return (!mHeader.getText().toString().isBlank() &
                !mDescription.getText().toString().isBlank());
    }

    boolean isFieldsChanged() {
        return !(mHeader.getText().toString().equals(mTask.getHeader())) |
                !(mDescription.getText().toString().equals(mTask.getDescription()));
    }
}