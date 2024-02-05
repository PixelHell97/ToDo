package com.pixel.todo_c39.ui.home.tasksList

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.pixel.todo_c39.R
import com.pixel.todo_c39.database.model.Task
import com.pixel.todo_c39.databinding.ItemTaskBinding
import com.pixel.todo_c39.ui.formatTime
import java.util.Calendar

class TaskAdapter(private var tasksList: MutableList<Task>? = null) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasksList!![position]
        val calendar = Calendar.Builder()
            .setInstant(task.time!!)
            .build()
        holder.bind(task, calendar)
        if (onTaskClickListener!=null) {
            holder.binding.dragItem.setOnClickListener {
                onTaskClickListener?.onItemClick(task, position)
            }
        }
        if (onIsDoneClickListener!=null) {
            holder.binding.taskIsDoneBtn.setOnClickListener {
                onIsDoneClickListener?.onItemClick(task, position)
            }
        }
        if (onDeleteClickListener!=null) {
            holder.binding.btnDeleteTask.setOnClickListener {
                onDeleteClickListener?.onItemClick(task, position)
            }
        }
    }

    override fun getItemCount(): Int = tasksList?.size ?: 0
    fun changeData(allTasks: List<Task>) {
        if (tasksList == null) {
            tasksList = mutableListOf()
        }
        tasksList?.clear()
        tasksList?.addAll(allTasks)
        notifyDataSetChanged()
    }

    var onTaskClickListener: OnItemClickListener? = null
    var onIsDoneClickListener: OnItemClickListener? = null
    var onDeleteClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(item: Task, position: Int)
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, calendar: Calendar) {
            binding.taskName.text = task.title.toString()
            binding.taskTime.text = calendar.formatTime()
            if (task.isDone == true) {
                binding.taskIsDoneBtn.setImageResource(R.drawable.ic_done)
                binding.taskName.setTextColor(Color.GREEN)
                binding.draggingBar.setImageResource(R.drawable.dragging_bar_done)
            }
        }
    }
}