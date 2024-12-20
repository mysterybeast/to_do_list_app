package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Data.DataBaseHandler
import com.example.todolist.Model.Task


class TaskListFragment : Fragment() {

    private var tasks = ArrayList<Task>()
    private var taskList: RecyclerView? = null
    private var dataBaseHandler: DataBaseHandler? = null
    private var createTaskButton: Button? = null
    private var emptyListLayout: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        dataBaseHandler = DataBaseHandler(requireActivity())

        taskList = view.findViewById(R.id.taskList)
        emptyListLayout = view.findViewById(R.id.emptyListLayout)
        createTaskButton = view.findViewById(R.id.createTaskButton)

        setIntialData()

        val taskAdapter = TaskAdapter(tasks, onTaskClick = { task: Task -> openTask(task) })

        createTaskButton!!.setOnClickListener {
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

        taskList!!.adapter = taskAdapter
        return view
    }

    private fun setIntialData() {
        tasks = dataBaseHandler!!.getAllTasks()

        if (tasks.isEmpty()) emptyListLayout!!.visibility = View.VISIBLE
        else emptyListLayout!!.visibility = View.GONE
    }

    private fun openTask(task: Task) {
        val idToPut = task.id
        val bundle = Bundle()
        bundle.putInt("id", idToPut)
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