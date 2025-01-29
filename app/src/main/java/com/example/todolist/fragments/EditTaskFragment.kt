package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todolist.App
import com.example.todolist.model.Task
import com.example.todolist.databinding.FragmentEditTaskBinding
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!
    private var header: EditText? = null
    private var description: EditText? = null
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val taskDao = App.instance.db.taskDao()
        val repository = TaskRepository(taskDao)

        header = binding.editTaskLT.header
        description = binding.editTaskLT.description
        val taskId = arguments?.getInt("id") ?: 0

        if (taskId > 0) {
            task = runBlocking {
                lifecycleScope.async(Dispatchers.IO) {
                    return@async repository.getTask(taskId)
                }.await()
            }
            header!!.setText(task!!.header)
            description!!.setText(task!!.description)
        }

        binding.backImageButton.setOnClickListener {
            if (isFieldsNotEmpty()) {
                task = Task(
                    id = taskId,
                    header = header!!.text.toString(),
                    description = description!!.text.toString()
                )
                lifecycleScope.launch { repository.saveTask(task!!) }
            }
            parentFragmentManager.popBackStack()
        }
    }

    private fun isFieldsNotEmpty(): Boolean {
        return (header!!.text.toString().isNotBlank() and
                description!!.text.toString().isNotBlank())
    }
}
