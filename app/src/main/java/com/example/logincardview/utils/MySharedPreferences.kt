package com.example.logincardview.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private var preferences: SharedPreferences? = null

    fun init(context: Context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        }
    }

    fun getToken(): String? {
        return preferences?.getString("token", null)
    }

    fun saveToken(token: String) {
        preferences?.edit()?.putString("token", token)?.apply()
    }

    fun clear() {
        preferences?.edit()?.clear()?.apply()
    }
}
