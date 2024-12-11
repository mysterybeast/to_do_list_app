package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.example.todolist.Model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    interface OnTaskClickListener {
        void onTaskClick(Task task, int position);
    }

    private final OnTaskClickListener mOnClickListener;
    private final LayoutInflater mInflater;
    private final List<Task> mTasks;

    TaskAdapter(Context context, List<Task> tasks, OnTaskClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        this.mTasks = tasks;
        this.mInflater = LayoutInflater.from(context);
    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder viewHolder;
//
//        if (convertView == null) {
//            convertView = inflater.inflate(this.layout, parent, false);
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        return convertView;
//    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task = mTasks.get(position);
        holder.header.setText(task.getHeader());
        holder.description.setText(task.getDescription());
        holder.itemView.setOnClickListener(v -> mOnClickListener.onTaskClick(task, position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView header, description;

        //CheckBox checkbox;
        ViewHolder(View view) {
            super(view);
            header = view.findViewById(R.id.header);
            description = view.findViewById(R.id.description);
            //checkbox = view.findViewById(R.id.checkbox);

        }
    }
}
