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

class EditTaskFragment : Fragment() {

    private var header: EditText? = null
    private var description: EditText? = null
    private var backImageButton: ImageButton? = null
    private var task: Task? = null
    private var taskId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)

        val dataBaseHandler = DataBaseHandler(requireActivity())

        header = view.findViewById(R.id.header)
        description = view.findViewById(R.id.description)
        backImageButton = view.findViewById(R.id.backImageButton)

        val bundle = arguments
        bundle?.let { taskId = it.getInt("id") }

        if (taskId > 0) {
            task = dataBaseHandler.getTask(taskId)
            header!!.setText(task!!.header)
            description!!.setText(task!!.description)
        }

        backImageButton!!.setOnClickListener {
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
        return view
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











