package com.pixel.todo_c39.ui.home.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pixel.todo_c39.database.TaskDatabase
import com.pixel.todo_c39.database.model.Task
import com.pixel.todo_c39.databinding.BottomSheetAddTaskBinding
import com.pixel.todo_c39.ui.formatDate
import com.pixel.todo_c39.ui.formatTime
import com.pixel.todo_c39.ui.getDateOnly
import com.pixel.todo_c39.ui.getTimeOnly
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddTaskBinding
    private val calender = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        binding.addTask.setOnClickListener {
            addTask()
        }
        binding.dateLayout.setOnClickListener {
            showDateDialog()
        }
        binding.timeLayout.setOnClickListener {
            showTimeDialog()
        }
    }

    private fun showTimeDialog() {
        val timePicker = TimePickerDialog(
            requireContext(),
            { view, hourOfDay, minute ->
                calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calender.set(Calendar.MINUTE, minute)
                binding.timeEditText.text = calender.formatTime()
                binding.timeLayout.error = null
            },
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            false,
        )
        timePicker.show()
    }

    private fun showDateDialog() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.dateEditText.text = calender.formatDate()
            binding.dateLayout.error = null
        }
        datePicker.show()
    }

    private fun isValidTask(): Boolean {
        var isValid = true
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        if (title.isBlank()) {
            binding.titleLayout.error = "required task title"
            isValid = false
        } else {
            binding.titleLayout.error = null
        }
        if (description.isBlank()) {
            binding.descriptionLayout.error = "required task description"
            isValid = false
        } else {
            binding.descriptionLayout.error = null
        }
        if (binding.timeEditText.text.isBlank()) {
            binding.timeLayout.error = "required task time"
            isValid = false
        }
        if (binding.dateEditText.text.isBlank()) {
            binding.dateLayout.error = "required task date"
            isValid = false
        }

        return isValid
    }

    private fun addTask() {
        if (!isValidTask()) {
            return
        }
        TaskDatabase.getInstance(requireContext())
            .getTaskDao()
            .insertTask(
                Task(
                    title = binding.titleEditText.text.toString(),
                    descriotion = binding.descriptionEditText.text.toString(),
                    date = calender.getDateOnly(),
                    time = calender.getTimeOnly(),
                    isDone = false,
                ),
            )
        Toast.makeText(requireContext(), "Task saved successfully", Toast.LENGTH_LONG).show()
        dismiss()
    }
}
