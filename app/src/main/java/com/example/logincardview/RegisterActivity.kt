package com.example.logincardview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.logincardview.databinding.ActivityRegisterBinding
import com.example.logincardview.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val pass = binding.passwordEditText.text.toString()
            val repeatPass = binding.passwordRepeatEditText.text.toString()

            if (email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty() || pass != repeatPass) {
                Toast.makeText(this, "Campos vacíos y/o passwords diferentes", Toast.LENGTH_LONG).show()
            } else {
                registerUser(email, pass)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = authRepository.register(email, password)

            runOnUiThread {
                if (success) {
                    Toast.makeText(this@RegisterActivity, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error al registrar usuario", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
