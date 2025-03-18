package com.example.authenticationapp

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity

class BiometricManager(private val activity: AppCompatActivity) {

    fun authenticateUser(callback: (Boolean) -> Unit) {
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                startBiometricPrompt(callback)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                callback(false)
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                callback(false)
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                callback(false)
            }
        }
    }

    private fun startBiometricPrompt(callback: (Boolean) -> Unit) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    callback(true)  // Authentication succeeded
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    callback(false)  // Authentication failed
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    callback(false)  // Authentication error
                }
            }
        )

        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setDescription("Please authenticate using your fingerprint or face.")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(biometricPromptInfo)
    }
}