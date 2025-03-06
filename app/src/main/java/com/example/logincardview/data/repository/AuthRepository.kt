package com.example.logincardview.repository

import com.example.logincardview.model.AuthResponse
import com.example.logincardview.model.UserRequest
import com.example.logincardview.network.RetrofitClient
import retrofit2.HttpException

class AuthRepository {
    suspend fun login(email: String, password: String): String? {
        return try {
            val response = RetrofitClient.instance.login(UserRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.token
            } else {
                null
            }
        } catch (e: HttpException) {
            null
        }
    }

    suspend fun register(email: String, password: String): Boolean {
        return try {
            val response = RetrofitClient.instance.register(UserRequest(email, password))
            response.isSuccessful
        } catch (e: HttpException) {
            false
        }
    }
}
