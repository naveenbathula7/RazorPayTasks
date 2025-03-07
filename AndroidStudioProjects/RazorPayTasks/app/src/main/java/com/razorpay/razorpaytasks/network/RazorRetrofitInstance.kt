package com.razorpay.razorpaytasks.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RazorRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RazorTaskApi by lazy {
        retrofit.create(RazorTaskApi::class.java)
    }
}