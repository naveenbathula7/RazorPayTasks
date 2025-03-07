package com.razorpay.razorpaytasks.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "razor_tasks")
data class RazorTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "task_title") val title: String,
    @ColumnInfo(name = "is_completed") val completed: Boolean
)
