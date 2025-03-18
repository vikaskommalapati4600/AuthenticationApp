package com.example.authenticationapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("validate-token")
    fun sendDataToServer(@Body data: RequestData): Call<ApiResponse>
}
