package com.example.todolist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.todolist.Data.DataBaseHandler;
import com.example.todolist.Model.Task;

import java.util.ArrayList;


public class TaskListFragment extends Fragment {

    private ArrayList<Task> mTasks = new ArrayList<>();
    private TaskAdapter mTaskAdapter;
    private RecyclerView mTaskList;
    private DataBaseHandler mDataBaseHandler;
    private Button mCreateTaskButton;
    private View mEmptyListLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mDataBaseHandler = new DataBaseHandler(getActivity());

        mTaskList = view.findViewById(R.id.taskList);
        mEmptyListLayout = view.findViewById(R.id.emptyListLayout);
        mCreateTaskButton = view.findViewById(R.id.createTaskButton);

        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        EditTaskFragment editTaskFragment = new EditTaskFragment();

        TaskAdapter.OnTaskClickListener taskClickListener = (task, position) -> {

            int idToPut = mTasks.get(position).getId();
            Bundle bundle = new Bundle();
            bundle.putInt("id", idToPut);
            taskDetailFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, taskDetailFragment)
                    .addToBackStack(null)
                    .commit();
        };

        mCreateTaskButton.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, editTaskFragment)
                .addToBackStack(null)
                .commit());

        setIntialData();
        mTaskAdapter = new TaskAdapter(getActivity(), mTasks, taskClickListener);
        mTaskList.setAdapter(mTaskAdapter);
        return view;
    }

    private void setIntialData() {
//        dataBaseHandler.addTask(new Task("Почитать книгу", "на свежем воздухе, желательно, лёжа в гамаке"));
//        dataBaseHandler.addTask(new Task("Сделать заготовки на зиму своими руками", "лечо, грибочки, огурцы, помидорки, варенье, кабачковая икра и т. п."));
//        dataBaseHandler.addTask(new Task("Запись в поликлинику", "28 декабря"));

        mTasks = mDataBaseHandler.getAllTasks();

        if (mTasks.isEmpty()) mEmptyListLayout.setVisibility(View.VISIBLE);
        else mEmptyListLayout.setVisibility(View.GONE);
    }
}