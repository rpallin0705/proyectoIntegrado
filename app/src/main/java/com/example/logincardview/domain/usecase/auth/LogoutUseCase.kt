package com.example.logincardview.domain.usecase.auth

import com.example.logincardview.network.RetrofitClient

class LogoutUseCase {
    suspend operator fun invoke(token: String): Boolean {
        return try{
            val response = RetrofitClient.authApi.logout("Bearer $token")
            response.isSuccessful
        } catch (e: Exception){
            false
        }
    }
}