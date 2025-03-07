package com.razorpay.razorpaytasks

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.ktx.analytics
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.razorpay.razorpaytasks.ui.theme.RazorPayTasksTheme
import org.json.JSONObject

class MainActivity : ComponentActivity(), PaymentResultListener {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var showFailureDialog by mutableStateOf(false)
    private var failureMessage by mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Checkout.preload(applicationContext)

        setContent {
            RazorPayTasksTheme {
                var amount by remember { mutableStateOf("") }
                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    "Payment Gateway Demo",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text("Enter Amount (INR)") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (amount.isNotEmpty() && amount.toInt() > 0) {
                                    startPayment(amount)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Enter a valid amount",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "PAY NOW", fontSize = 18.sp)
                        }
                    }
                }

                if (showFailureDialog) {
                    PaymentFailureDialog(failureMessage) {
                        showFailureDialog = false
                    }
                }
            }
        }
    }

    private fun startPayment(amount: String) {
        val activity: Activity = this
        val checkout = Checkout()
        checkout.setKeyID(RazorConstants.RAZORPAY_KEY)

        try {
            val options = JSONObject()
            options.put("name", RazorConstants.COMPANY_NAME)
            options.put("description", RazorConstants.PAYMENT_DESCRIPTION)
            options.put("image", RazorConstants.LOGO_URL)
            options.put("theme.color", RazorConstants.THEME_COLOR)
            options.put("currency", "INR")
            options.put("amount", (amount.toInt() * 200).toString())

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", RazorConstants.USER_EMAIL)
            prefill.put("contact", RazorConstants.USER_PHONE)

            options.put("prefill", prefill)
            checkout.open(activity, options)
        } catch (e: Exception) {
            throw RuntimeException("Payment Exception: ${e.message}", e)
        }
    }

    override fun onPaymentSuccess(paymentId: String?) {
        Toast.makeText(this, "Payment Successful: $paymentId", Toast.LENGTH_LONG).show()
        firebaseAnalytics.logEvent("payment_Successful", Bundle().apply {
            putString("payment", paymentId)
        })
    }

    override fun onPaymentError(code: Int, response: String?) {
        failureMessage = "Payment Not done"
        showFailureDialog = true
        firebaseAnalytics.logEvent("payment_not_done", Bundle().apply {
            putString("payments", "Payment Not Done")
        })
    }

    @Composable
    fun PaymentFailureDialog(errorMessage: String, onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Payment Failed") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}
