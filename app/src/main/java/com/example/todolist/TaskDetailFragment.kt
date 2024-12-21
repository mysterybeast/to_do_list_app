package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.Data.DataBaseHandler
import com.example.todolist.databinding.FragmentTaskDetailBinding


class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dataBaseHandler = DataBaseHandler(requireActivity())

        val header = binding.taskLT.header
        val description = binding.taskLT.description
        val changeTaskButton = binding.changeTaskButton
        val deleteTaskButton = binding.deleteTaskButton
        val backImageButton = binding.backImageButton

        header.ellipsize = null
        header.isSingleLine = false
        description.ellipsize = null
        description.maxLines = 20

        val taskId = requireArguments().getInt("id")
        val task = dataBaseHandler.getTask(taskId)
        header.text = task.header
        description.text = task.description

        changeTaskButton.setOnClickListener {
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

        deleteTaskButton.setOnClickListener {
            dataBaseHandler.deleteTask(task)
            parentFragmentManager.popBackStack()
        }

        backImageButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}