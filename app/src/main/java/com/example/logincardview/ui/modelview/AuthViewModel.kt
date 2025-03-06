package com.example.logincardview.ui.modelview

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logincardview.domain.usecase.auth.LogoutUseCase
import com.example.logincardview.utils.MySharedPreferences
import kotlinx.coroutines.launch

class AuthViewModel(context: Context) : ViewModel() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    private val logoutUseCase = LogoutUseCase()

    fun logout(onLogoutComplete: () -> Unit, onError: (String) -> Unit) {
        val token = sharedPreferences.getString("token", null)

        if (token == null) {
            onError("Token no encontrado")
            return
        }

        viewModelScope.launch {
            val success = logoutUseCase(token)

            if (success) {
                sharedPreferences.edit().clear().apply()
                MySharedPreferences.clear()
                onLogoutComplete()
            } else {
                onError("Error al cerrar sesión")
            }
        }
    }
}

