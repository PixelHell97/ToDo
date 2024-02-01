package com.pixel.todo_c39.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pixel.todo_c39.R
import com.pixel.todo_c39.databinding.ActivityHomeBinding
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
            findNavController(R.id.fragment_container).navigate(R.id.navigate_to_addTaskBottomSheet)
        }
        //binding.homeBottomNavBar.setupWithNavController(findNavController(R.id.fragment_container))
        binding.content.homeBottomNavBar.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.tasksListFragment -> {
                    TasksListFragment()
                }

                R.id.settingsFragment -> {
                    SettingsFragment()
                }

                else -> {
                    TasksListFragment()
                }
            }
            pushFragment(fragment)
            true
        }
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}