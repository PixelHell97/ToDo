package com.pixel.todo_c39.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pixel.todo_c39.R
import com.pixel.todo_c39.databinding.ActivityHomeBinding
import com.pixel.todo_c39.ui.home.addTask.AddTaskBottomSheet
import com.pixel.todo_c39.ui.home.settings.SettingsFragment
import com.pixel.todo_c39.ui.home.tasksList.TasksListFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.content.addTaskFab.setOnClickListener {
            showAddTaskBottomSheet()
        }
        binding.content
            .homeBottomNavBar
            .setOnItemSelectedListener {
                val fragment = when (it.itemId) {
                    R.id.nav_task_list_fragment -> {
                        binding.fragmentTitle.setText(R.string.to_do_list)
                        TasksListFragment()
                    }
                    R.id.nav_settings_fragment -> {
                        binding.fragmentTitle.setText(R.string.settings)
                        SettingsFragment()
                    }
                    else -> TasksListFragment()
                }
                pushFragment(fragment)
                true
            }
        binding.fragmentTitle.setText(R.string.to_do_list)
        binding.content.homeBottomNavBar.selectedItemId = R.id.nav_task_list_fragment
    }

    private fun showAddTaskBottomSheet() {
        val addTask = AddTaskBottomSheet()
        addTask.show(supportFragmentManager, null)
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}