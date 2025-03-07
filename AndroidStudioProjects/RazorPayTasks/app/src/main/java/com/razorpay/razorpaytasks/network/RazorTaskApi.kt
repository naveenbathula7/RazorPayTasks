package com.razorpay.razorpaytasks.network

import com.razorpay.razorpaytasks.data.RazorTask
import retrofit2.http.GET

interface RazorTaskApi {
    @GET("todos")
    suspend fun getTasks(): List<RazorTask>
}