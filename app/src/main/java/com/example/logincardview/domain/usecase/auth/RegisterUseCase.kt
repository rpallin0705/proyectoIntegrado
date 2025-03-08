package com.example.logincardview.domain.usecase

import com.example.logincardview.model.UserRequest
import com.example.logincardview.network.RetrofitClient

class RegisterUseCase {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return try {
            val response = RetrofitClient.authApi.register(UserRequest(email, password))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
