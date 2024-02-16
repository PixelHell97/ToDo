package com.pixel.todo_c39.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pixel.todo_c39.database.model.Task

@Dao
interface TaskDao {

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("select * from Task")
    fun getAllTasks(): List<Task>

    @Query("select * from Task where date = :date order by time ASC")
    fun getAllTasksByDate(date: Long): List<Task>
}
