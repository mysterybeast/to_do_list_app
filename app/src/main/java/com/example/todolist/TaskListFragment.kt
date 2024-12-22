package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.todolist.Data.DataBaseHandler
import com.example.todolist.Model.Task
import com.example.todolist.databinding.FragmentTaskListBinding


class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private var tasks = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setIntialData()
        val taskList = binding.taskListLayout.taskList
        val taskAdapter = TaskAdapter(tasks, onTaskClick = { task: Task -> openTask(task.id) })
        taskList.adapter = taskAdapter

        binding.createTaskButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.slide_out
                )
                .replace(R.id.fragment_container, EditTaskFragment::class.java, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setIntialData() {
        val dataBaseHandler = DataBaseHandler(requireActivity())
        tasks = dataBaseHandler.getAllTasks()
        binding.emptyListLayout.isVisible = tasks.isEmpty()
    }

    private fun openTask(taskId: Int) {
        val bundle = bundleOf("id" to taskId)

        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.fragment_container, TaskDetailFragment::class.java, bundle)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}