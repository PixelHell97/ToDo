package com.pixel.todo_c39.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pixel.todo_c39.database.dao.TaskDao
import com.pixel.todo_c39.database.model.Task

@Database(entities = [Task::class], version = 3, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        private var database: TaskDatabase? = null
        private const val database_KAY = "taskDatabase"
        fun getInstance(context: Context): TaskDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    database_KAY,
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }
}
