package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.todolist.Data.DataBaseHandler
import com.example.todolist.Model.Task
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
        header = binding.editTaskLT.header
        description = binding.editTaskLT.description
        val backImageButton = binding.backImageButton
        val dataBaseHandler = DataBaseHandler(requireActivity())
        val taskId = arguments?.getInt("id") ?: 0

        if (taskId > 0) {
            task = dataBaseHandler.getTask(taskId)
            header!!.setText(task!!.header)
            description!!.setText(task!!.description)
        }

        backImageButton.setOnClickListener {
            if (taskId > 0 && isFieldsChanged() && isFieldsNotEmpty()) {
                task = Task(
                    taskId, header!!.text.toString(),
                    description!!.text.toString()
                )
                dataBaseHandler.updateTask(task!!)
            } else if (taskId == 0 && isFieldsNotEmpty()) {
                task = Task(
                    header!!.text.toString(),
                    description!!.text.toString()
                )
                dataBaseHandler.addTask(task!!)
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
