package com.example.logincardview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.logincardview.databinding.LoginActivityBinding
import com.example.logincardview.ui.modelview.AuthViewModel
import com.example.logincardview.ui.views.activity.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.provideFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loadEmail()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password,
                    onSuccess = {
                        Toast.makeText(this, "Login exitoso", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    },
                    onError = { errorMessage ->
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                )
            } else {
                Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
            }
        }

        binding.singUpText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loadEmail() {
        val savedEmail = authViewModel.getSavedEmail()
        binding.emailEditText.setText(savedEmail)
    }
}
