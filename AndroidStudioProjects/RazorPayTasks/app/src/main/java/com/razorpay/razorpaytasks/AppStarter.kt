package com.razorpay.razorpaytasks

import android.app.Application
import com.google.firebase.FirebaseApp


class AppStarter : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

    }
}
