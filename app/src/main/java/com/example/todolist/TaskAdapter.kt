package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Model.Task
import com.example.todolist.databinding.TaskLayoutBinding


class TaskAdapter(
    private val tasks: List<Task>,
    val onTaskClick: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewHolder(private val binding: TaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var item: Task? = null
        fun bind(taskItem: Task) {
            item = taskItem
            binding.header.text = taskItem.header
            binding.description.text = taskItem.description
        }

        init {
            itemView.setOnClickListener {
                item?.let { onTaskClick(it) }
            }
        }
    }
}