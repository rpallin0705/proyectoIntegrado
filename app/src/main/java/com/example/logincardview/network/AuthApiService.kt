package com.example.logincardview.network

import com.example.logincardview.model.AuthResponse
import com.example.logincardview.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("/auth/login")
    suspend fun login(@Body request: UserRequest): Response<AuthResponse>

    @POST("/auth/register")
    suspend fun register(@Body request: UserRequest): Response<Unit>

    @POST("/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

}
