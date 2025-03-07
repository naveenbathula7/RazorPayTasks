package com.razorpay.razortasks.ui.viewmodel

import RazorTaskRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.razorpay.razorpaytasks.data.RazorTask
import com.razorpay.razorpaytasks.data.RazorTaskDatabase
import kotlinx.coroutines.launch

class RazorTaskViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository: RazorTaskRepository
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    init {
        val taskDao = RazorTaskDatabase.getDatabase(application).taskDao()
        repository = RazorTaskRepository(taskDao)
        firebaseAnalytics = FirebaseAnalytics.getInstance(application)
    }

    val tasks = repository.allTasks.asLiveData()

    fun fetchTasksFromAPI() {
        viewModelScope.launch {
            repository.fetchTasksFromAPI()
            firebaseAnalytics.logEvent("task_fetch_success", null)
        }
    }

    fun addTask(title: String) {
        viewModelScope.launch {
            val task = RazorTask(title = title, completed = false)
            repository.insertTask(task)
            firebaseAnalytics.logEvent("razor_task_added", null)
        }
    }

    fun markTaskComplete(task: RazorTask) {
        viewModelScope.launch {
            val updatedTask = task.copy(completed = true)
            repository.updateTask(updatedTask)
            firebaseAnalytics.logEvent("razor_task_completed", null)
        }
    }
}
