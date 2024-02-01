package com.pixel.todo_c39.ui.home.tasksList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pixel.todo_c39.R
import com.pixel.todo_c39.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {
    private lateinit var binding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_list, container, false)
    }
}