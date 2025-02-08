package com.example.logincardview.ui.views.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.logincardview.LoginActivity
import com.example.logincardview.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var switchAdmin: SwitchCompat
    private lateinit var logoutButton: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        nameTextView = view.findViewById(R.id.settingg_name)
        emailTextView = view.findViewById(R.id.settings_email)
        switchAdmin = view.findViewById(R.id.setting_admin_btn)
        logoutButton = view.findViewById(R.id.setting_logout_btn)


        if (!sharedPreferences.contains("isAdmin")) {
            sharedPreferences.edit().putBoolean("isAdmin", false).apply()
        }

        loadUserData()

        switchAdmin.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("isAdmin", isChecked).apply()
            Toast.makeText(requireContext(), "Admin: ${if (isChecked) "Activado" else "Desactivado"}", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            // Limpiar SharedPreferences
            sharedPreferences.edit().clear().apply()

            // Cerrar sesión en Firebase
            FirebaseAuth.getInstance().signOut()

            // Redirigir a LoginActivity y limpiar el backstack
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun loadUserData() {
        val email = sharedPreferences.getString("saved_email", "Correo no disponible")
        val isAdmin = sharedPreferences.getBoolean("isAdmin", false)

        nameTextView.text = email?.substringBefore('@')
        emailTextView.text = email
        switchAdmin.isChecked = isAdmin
    }
}
