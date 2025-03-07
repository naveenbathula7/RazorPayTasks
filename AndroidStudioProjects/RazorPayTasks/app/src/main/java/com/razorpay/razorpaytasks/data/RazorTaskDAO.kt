package com.razorpay.razorpaytasks.data

import androidx.room.*
import com.razorpay.razorpaytasks.data.RazorTask
import kotlinx.coroutines.flow.Flow

@Dao
interface RazorTaskDao {
    @Query("SELECT * FROM razor_tasks")
    fun getAllTasks(): Flow<List<RazorTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: RazorTask)

    @Update
    suspend fun updateTask(task: RazorTask)
}
