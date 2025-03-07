# RazorPayTasks
1.Setup and Run Instructions
1.1. Clone the Repository
   git clone https://github.com/naveenbathula7/RazorPayTasks.git
   cd RazorpayTaskManager

1.2. Open in Android Studio
   - Open Android Studio → Click "Open an existing project" → Select the cloned repo.

1.3. Add Dependencies (in build.gradle (Module))
   dependencies {
       // Razorpay SDK
       implementation 'com.razorpay:checkout:1.6.41'

       // Firebase
       implementation 'com.google.firebase:firebase-analytics-ktx'
       implementation 'com.google.firebase:firebase-crashlytics-ktx'
   }

1.4. Configure Firebase
   - Enable Analytics & Crashlytics in the Firebase Console.
   - Download google-services.json and place it inside app/.

1.5. Add Razorpay Key
   - Replace "YOUR_RAZORPAY_KEY" in MainActivity.kt with your Razorpay API key.

1.6. Run the App
   - Connect a real device/emulator.
   - Click Run (▶️) in Android Studio.
   - The app should launch successfully.

2.Third-Party Library Integration
2.1. Razorpay Payment Gateway
   - Used for in-app payments.
   - Preloads Checkout for better performance.
   - Handles success & failure responses.

2.2. Firebase Analytics
   - Tracks payment success & failure events.
   - Helps in user behavior analysis.

2.3. Firebase Crashlytics
   - Records crashes related to payments & DB errors.
   - Helps in debugging production issues.

3.Design Decisions
3.1. Clean Architecture (MVVM)
   - Separation of concerns for maintainability & scalability.

3.2. Jetpack Compose UI
   - Declarative UI for faster & flexible development.

3.3. Firebase Integration
   - Enables real-time monitoring for issues & performance.

3.4. Razorpay Integration
   - Ensures secure and reliable transactions.

4.Firebase Analytics Events
- Event Name: payment_success
  - Trigger: When payment is successful.
  - Parameters: { "transaction_id": "xyz123", "amount": "200" }

- Event Name: payment_failure
  - Trigger: When payment fails.
  - Parameters: { "error_code": "400", "reason": "Insufficient Funds" }

4.2Firebase Crashlytics Events
- Database Crash Simulation
  - Force a crash by inserting an invalid entry.
  - Observe error logs on Firebase Console.

- Payment Failure Crash Simulation
  - Force a crash when Razorpay throws an exception.

5.Screenshots & Recordings
5.1. Firebase Analytics Events
   - Screenshot of successful & failed payment logs.

5.2. Firebase Crashlytics Logs
   - Screenshot of app crashes due to Razorpay or DB errors.

5.3. Network Performance Monitoring
   - Screenshot of Firebase Performance Metrics.

5.4. Screen Recording
   - Video showing payment process, crashes & Firebase logs.
