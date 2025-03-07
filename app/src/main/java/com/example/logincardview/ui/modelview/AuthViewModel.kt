package com.example.logincardview.ui.modelview

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.logincardview.repository.AuthRepository
import com.example.logincardview.utils.MySharedPreferences
import kotlinx.coroutines.launch

class AuthViewModel(context: Context) : ViewModel() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    private val authRepository = AuthRepository()

    init {
        MySharedPreferences.init(context)
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return AuthViewModel(context) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val success = authRepository.login(email, password)
            if (success) {
                sharedPreferences.edit().putString("saved_email", email).apply()
                onSuccess()
            } else {
                onError("Error al iniciar sesión")
            }
        }
    }

    fun register(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val success = authRepository.register(email, password)
            if (success) {
                onSuccess()
            } else {
                onError("Error al registrarse")
            }
        }
    }

    fun logout(onLogoutComplete: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val success = authRepository.logout()
            if (success) {
                sharedPreferences.edit().clear().apply()
                onLogoutComplete()
            } else {
                onError("Error al cerrar sesión")
            }
        }
    }

    fun getSavedEmail(): String {
        return sharedPreferences.getString("saved_email", "") ?: ""
    }

}
