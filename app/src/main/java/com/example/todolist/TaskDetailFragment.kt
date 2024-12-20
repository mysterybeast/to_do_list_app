package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todolist.Data.DataBaseHandler
import com.example.todolist.Model.Task


class TaskDetailFragment : Fragment() {

    private var header: TextView? = null
    private var description: TextView? = null
    private var changeTaskButton: Button? = null
    private var deleteTaskButton: Button? = null
    private var backImageButton: ImageButton? = null
    private var dataBaseHandler: DataBaseHandler? = null
    private var taskId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)

        dataBaseHandler = DataBaseHandler(requireActivity())

        header = view.findViewById(R.id.header)
        description = view.findViewById(R.id.description)
        changeTaskButton = view.findViewById(R.id.changeTaskButton)
        deleteTaskButton = view.findViewById(R.id.deleteTaskButton)
        backImageButton = view.findViewById(R.id.backImageButton)

        header!!.ellipsize = null
        header!!.isSingleLine = false
        description!!.ellipsize = null
        description!!.maxLines = 20

        taskId = requireArguments().getInt("id")
        val task: Task = dataBaseHandler!!.getTask(taskId)
        header!!.text = task.header
        description!!.text = task.description

        changeTaskButton!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", taskId)

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.slide_out
                )
                .replace(R.id.fragment_container, EditTaskFragment::class.java, bundle)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }

        deleteTaskButton!!.setOnClickListener {
            dataBaseHandler!!.deleteTask(task)
            parentFragmentManager.popBackStack()
        }

        backImageButton!!.setOnClickListener { parentFragmentManager.popBackStack() }
        return view
    }
}