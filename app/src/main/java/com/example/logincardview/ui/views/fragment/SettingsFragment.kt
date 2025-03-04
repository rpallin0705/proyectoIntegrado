package com.example.logincardview.ui.views.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.logincardview.LoginActivity
import com.example.logincardview.R
import com.example.logincardview.network.RetrofitClient
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var switchAdmin: SwitchCompat
    private lateinit var logoutButton: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las vistas
        nameTextView = view.findViewById(R.id.settingg_name)
        emailTextView = view.findViewById(R.id.settings_email)
        switchAdmin = view.findViewById(R.id.setting_admin_btn)
        logoutButton = view.findViewById(R.id.setting_logout_btn)

        sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        val isAdmin = sharedPreferences.getBoolean("is_admin", false)
        switchAdmin.isChecked = isAdmin

        switchAdmin.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("is_admin", isChecked).apply()
            Toast.makeText(requireContext(), "Modo administrador ${if (isChecked) "activado" else "desactivado"}", Toast.LENGTH_SHORT).show()
        }

        loadUserData()

        // Nuevo logout con API
        logoutButton.setOnClickListener {
            lifecycleScope.launch {
                logoutUser()
            }
        }
    }

    private fun loadUserData() {
        val email = sharedPreferences.getString("saved_email", "Correo no disponible")
        nameTextView.text = email?.substringBefore('@')
        emailTextView.text = email
    }

    private suspend fun logoutUser() {
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            try {
                val response = RetrofitClient.instance.logout("Bearer $token")
                if (response.isSuccessful) {
                    sharedPreferences.edit().clear().apply()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Error al cerrar sesión", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "Token no encontrado", Toast.LENGTH_LONG).show()
        }
    }
}
