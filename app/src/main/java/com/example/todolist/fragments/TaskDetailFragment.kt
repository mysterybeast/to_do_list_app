package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todolist.App
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTaskDetailBinding
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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

        val taskDao = App.instance.db.taskDao()
        val repository = TaskRepository(taskDao)
        val taskId = requireArguments().getInt("id")

        val task = runBlocking {
            lifecycleScope.async(Dispatchers.IO) {
                return@async repository.getTask(taskId)
            }.await()
        }

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
            lifecycleScope.launch { repository.deleteTask(task) }
            parentFragmentManager.popBackStack()
        }

        binding.backImageButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}