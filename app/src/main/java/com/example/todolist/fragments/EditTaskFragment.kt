package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.viewmodel.TaskListViewModel
import com.example.todolist.model.Task
import com.example.todolist.databinding.FragmentEditTaskBinding


class EditTaskFragment : Fragment() {


    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!
    private var header: EditText? = null
    private var description: EditText? = null
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val taskListViewModel = ViewModelProvider(this)[TaskListViewModel::class.java]

        header = binding.editTaskLT.header
        description = binding.editTaskLT.description
        val taskId = arguments?.getInt("id") ?: 0

        if (taskId > 0) {
            task = taskListViewModel.getTask(taskId)
            header!!.setText(task!!.header)
            description!!.setText(task!!.description)
        }

        binding.backImageButton.setOnClickListener {
            if (taskId > 0 && isFieldsChanged() && isFieldsNotEmpty()) {
                task = Task(
                    id = taskId,
                    header = header!!.text.toString(),
                    description = description!!.text.toString()
                )
                taskListViewModel.updateTask(task!!)
            } else if (taskId == 0 && isFieldsNotEmpty()) {
                task = Task(
                    id = 0,
                    header = header!!.text.toString(),
                    description = description!!.text.toString()
                )
                taskListViewModel.addTask(task!!)
            }
            parentFragmentManager.popBackStack()
        }
    }

    private fun isFieldsNotEmpty(): Boolean {
        return (header!!.text.toString().isNotBlank() and
                description!!.text.toString().isNotBlank())
    }

    private fun isFieldsChanged(): Boolean {
        return (header!!.text.toString() != task!!.header) or
                (description!!.text.toString() != task!!.description)
    }
}
