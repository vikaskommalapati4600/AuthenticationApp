# Android Biometric Authentication App

A simple Android app that demonstrates biometric authentication (fingerprint or face recognition) and integrates with a backend for token validation.

---

## Features
- Biometric authentication using Android's `BiometricPrompt` API.
- Token generation with user ID, device ID, and expiration timestamp.
- Backend integration for token validation.

---

## Setup
1. Backend:
   - Navigate to the `backend` folder and run:
     ```bash
     python app.py
     ```
   - The backend will start at `http://127.0.0.1:5000`.

2. Android App:
   - Open the project in Android Studio.
   - Update the `BASE_URL` in `RetrofitClient.kt` to point to your backend.
   - Build and run the app on an Android device or emulator.

---

## How to Use
1. Enter your email in the app.
2. Click "Authenticate" and perform biometric authentication.
3. If successful, the app will generate a token and send it to the backend for validation.

---

## Technologies Used
- **Frontend**: Android (Kotlin)
- **Backend**: Python Flask
- **Libraries**: Retrofit, Biometric API

---
