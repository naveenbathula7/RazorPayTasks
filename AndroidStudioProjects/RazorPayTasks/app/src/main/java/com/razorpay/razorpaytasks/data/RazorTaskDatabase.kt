package com.razorpay.razorpaytasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RazorTask::class], version = 1, exportSchema = false)
abstract class RazorTaskDatabase : RoomDatabase() {

    abstract fun taskDao(): RazorTaskDao

    companion object {
        @Volatile
        private var INSTANCE: RazorTaskDatabase? = null

        fun getDatabase(context: Context): RazorTaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RazorTaskDatabase::class.java,
                    "razor_task_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
