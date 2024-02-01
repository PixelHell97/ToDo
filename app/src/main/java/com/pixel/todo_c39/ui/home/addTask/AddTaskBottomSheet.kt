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
import java.text.SimpleDateFormat
import java.util.Calendar


class AddTaskBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddTaskBinding
    private val calender = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        closeBottomSheet()
    }

    private fun closeBottomSheet() {
        activity?.supportFragmentManager?.popBackStack()
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
            false
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
        if (!isValidTask())
            return
        TaskDatabase.getInstance(requireContext())
            .getTaskDao()
            .insertTask(
                Task(
                    title = binding.titleEditText.text.toString(),
                    descriotion = binding.descriptionEditText.text.toString(),
                    date = calender.getDateOnly(),
                    time = calender.getTimeOnly(),
                    isDone = false
                )
            )
        Toast.makeText(requireContext(), "Task saved successfully", Toast.LENGTH_LONG).show()
    }
}

fun Calendar.getTimeOnly(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(
        0, 0, 0,
        get(Calendar.HOUR_OF_DAY),
        get(Calendar.MINUTE),
        0
    )
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Calendar.getDateOnly(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH),
        0, 0, 0
    )
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Calendar.formatTime(): String {
    val formatter = SimpleDateFormat("hh:mm a")
    return formatter.format(time)
}

fun Calendar.formatDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(time)
}