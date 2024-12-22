package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
        val taskId = requireArguments().getInt("id")
        val task = dataBaseHandler.getTask(taskId)

        binding.taskLT.header.apply {
            ellipsize = null
            isSingleLine = false
            text = task.header
        }

        binding.taskLT.description.apply {
            ellipsize = null
            maxLines = 20
            text = task.description
        }

        binding.changeTaskButton.setOnClickListener {
            val bundle = bundleOf("id" to taskId)

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

        binding.deleteTaskButton.setOnClickListener {
            dataBaseHandler.deleteTask(taskId)
            parentFragmentManager.popBackStack()
        }

        binding.backImageButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}