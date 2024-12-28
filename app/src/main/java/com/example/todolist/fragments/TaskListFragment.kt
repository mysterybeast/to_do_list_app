package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.R
import com.example.todolist.TaskAdapter
import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.repository.TaskRepository


class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private var tasks: LiveData<List<Task>> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setIntialData()
        val taskList = binding.taskListLayout.taskList
        val taskAdapter = TaskAdapter(onTaskClick = { task: Task -> openTask(task.id) })

        tasks.observe(viewLifecycleOwner) { task ->
            taskAdapter.setTasks(task)
            binding.emptyListLayout.isVisible = task.isEmpty()
        }
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
        val taskDao = AppDatabase.getDataBase(requireContext()).taskDao()
        val repository = TaskRepository(taskDao)
        tasks = repository.getAllTasks
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