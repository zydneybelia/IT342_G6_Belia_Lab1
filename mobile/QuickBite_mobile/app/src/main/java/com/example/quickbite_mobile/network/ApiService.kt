package com.example.quickbite_mobile.network

import com.example.quickbite_mobile.data.model.LoginRequest
import com.example.quickbite_mobile.data.model.LoginResponse
import com.example.quickbite_mobile.data.model.RegisterRequest
import com.example.quickbite_mobile.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}
