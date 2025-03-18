package com.example.authenticationapp
import android.util.Log

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var authenticateButton: Button
    private lateinit var statusText: TextView
    private val biometricManager = BiometricManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailInput = findViewById(R.id.emailInput)
        authenticateButton = findViewById(R.id.authenticateButton)
        statusText = findViewById(R.id.statusText)

        authenticateButton.setOnClickListener {
            val email = emailInput.text.toString()
            if (email.isNotEmpty()) {
                statusText.text = "Authenticating..."
                biometricManager.authenticateUser { success ->
                    if (success) {
                        statusText.text = "Authentication successful. Submitting email..."
                        // Generate token and send it to the backend
                        sendToBackend(email)
                    } else {
                        statusText.text = "Authentication failed. Please try again."
                    }
                }
            } else {
                statusText.text = "Please enter a valid email."
            }
        }
    }

    private fun sendToBackend(email: String) {
        val token = generateToken()

        val requestData = RequestData(email, token)

        RetrofitClient.instance.sendDataToServer(requestData).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == "success") {
                        statusText.text = "Email submitted successfully!"
                    } else {
                        statusText.text = "Backend validation failed: ${apiResponse?.message}"
                    }
                } else {
                    statusText.text = "Server error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                statusText.text = "Network error: ${t.message}"
            }
        })
    }

    private fun generateToken(): String {
        val userId = "user-id-12345"
        val deviceId = "device-id-67890"
        val expirationTimestamp = System.currentTimeMillis() + 3600000
        return "$userId-$deviceId-expiry-$expirationTimestamp"
    }

}