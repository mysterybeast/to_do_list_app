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

    private ArrayList<Task> tasks = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private RecyclerView taskList;
    private DataBaseHandler dataBaseHandler;
    private Button createTaskButton;
    private View emptyListLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        dataBaseHandler = new DataBaseHandler(getActivity());

        taskList = view.findViewById(R.id.taskList);
        emptyListLayout = view.findViewById(R.id.emptyListLayout);
        createTaskButton = view.findViewById(R.id.createTaskButton);

        TaskAdapter.OnTaskClickListener taskClickListener = (task, position) -> {
            int idToPut = tasks.get(position).getId();
            Bundle bundle = new Bundle();
            bundle.putInt("id", idToPut);

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.fade_out,
                            R.anim.fade_in, R.anim.slide_out)
                    .replace(R.id.fragment_container, TaskDetailFragment.class, bundle)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        };

        createTaskButton.setOnClickListener(v -> getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.fragment_container, EditTaskFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit());

        setIntialData();
        taskAdapter = new TaskAdapter(getActivity(), tasks, taskClickListener);
        taskList.setAdapter(taskAdapter);
        return view;
    }

    private void setIntialData() {
        tasks = dataBaseHandler.getAllTasks();

        if (tasks.isEmpty()) emptyListLayout.setVisibility(View.VISIBLE);
        else emptyListLayout.setVisibility(View.GONE);
    }
}