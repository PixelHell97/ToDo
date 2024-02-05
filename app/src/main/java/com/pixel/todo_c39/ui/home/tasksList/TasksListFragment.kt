package com.pixel.todo_c39.ui.home.tasksList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pixel.todo_c39.database.TaskDatabase
import com.pixel.todo_c39.database.model.Task
import com.pixel.todo_c39.databinding.FragmentTasksListBinding
import com.pixel.todo_c39.ui.Constants
import com.pixel.todo_c39.ui.getDateOnly
import com.pixel.todo_c39.ui.home.editTask.EditTaskActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener
import java.util.Calendar

class TasksListFragment : Fragment() {
    private lateinit var binding: FragmentTasksListBinding
    private val currentDate = Calendar.getInstance()
    private var adapter = TaskAdapter()

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun retrieveData() {
        val allTasks = TaskDatabase.getInstance(requireContext())
            .getTaskDao()
            .getAllTasksByDate(currentDate.getDateOnly())
        adapter.changeData(allTasks)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        editTask()
        deleteTask()
        taskDone()
    }

    private fun taskDone() {
        adapter.onIsDoneClickListener = TaskAdapter
            .OnItemClickListener { item, position ->
                item.isDone = true
                TaskDatabase.getInstance(requireContext())
                    .getTaskDao()
                    .updateTask(item)
                adapter.notifyItemChanged(position)
            }
    }

    private fun deleteTask() {
        adapter.onDeleteClickListener = TaskAdapter
            .OnItemClickListener { item, position ->
                TaskDatabase.getInstance(requireContext())
                    .getTaskDao()
                    .deleteTask(item)
                retrieveData()
            }
    }

    private fun editTask() {
        adapter.onTaskClickListener = TaskAdapter.OnItemClickListener { item, _ ->
            openEditActivity(item)
        }
    }

    private fun openEditActivity(task: Task) {
        val intent = Intent(activity, EditTaskActivity::class.java)
        intent.putExtra(Constants.TASK_KAY, task)
        startActivity(intent)
    }

    private fun initRecycler() {
        binding.tasksRecycler.adapter = adapter
        binding.calendarView.selectedDate = CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                currentDate.set(date.year,date.month-1,date.day)
                retrieveData()
            }
        }
    }
}