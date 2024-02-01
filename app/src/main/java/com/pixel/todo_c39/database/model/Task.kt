package com.pixel.todo_c39.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo var title: String? = null,
    @ColumnInfo var descriotion: String? = null,
    @ColumnInfo var date: Long? = null,
    @ColumnInfo var time: Long? = null,
    @ColumnInfo var isDone: Boolean? = false
) : Parcelable
