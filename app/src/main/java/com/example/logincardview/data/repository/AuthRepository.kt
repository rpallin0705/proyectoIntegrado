package com.example.logincardview.repository

import android.util.Log
import com.example.logincardview.model.AuthResponse
import com.example.logincardview.model.UserRequest
import com.example.logincardview.network.RetrofitClient
import com.example.logincardview.utils.MySharedPreferences
import retrofit2.Response

class AuthRepository {

    suspend fun login(email: String, password: String): Boolean {
        return try {
            val response: Response<AuthResponse> = RetrofitClient.authApi.login(UserRequest(email, password))
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


    suspend fun register(email: String, password: String): Boolean {
        return try {
            val response: Response<Unit> = RetrofitClient.authApi.register(UserRequest(email, password))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun logout(): Boolean {
        val token = MySharedPreferences.getToken() ?: return false

        return try {
            val response: Response<Unit> = RetrofitClient.authApi.logout("Bearer $token")

            if (response.isSuccessful) {

                MySharedPreferences.clear()
            }

            response.isSuccessful
        } catch (e: Exception) {
            Log.e("AuthRepository", "Excepción en logout: ${e.message}")
            false
        }
    }


}
