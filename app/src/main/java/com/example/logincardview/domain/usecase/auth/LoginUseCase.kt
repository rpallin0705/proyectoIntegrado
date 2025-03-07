package com.example.logincardview.domain.usecase.auth

import com.example.logincardview.model.UserRequest
import com.example.logincardview.network.RetrofitClient
import com.example.logincardview.utils.MySharedPreferences

class LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return try {
            val response = RetrofitClient.authApi.login(UserRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.token
                MySharedPreferences.saveToken(token)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}