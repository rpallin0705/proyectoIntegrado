package com.example.logincardview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logincardview.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: LoginActivityBinding

    companion object {
        private const val MYUSER = "usuario"
        private const val MYPASS = "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        loginActivityBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(loginActivityBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        start()

    }

    private fun start() {

        loginActivityBinding.loginButton.setOnClickListener {
            val username = loginActivityBinding.usernameEditTxt.text.toString()  // Obtener el texto del username
            val password = loginActivityBinding.passwordEditTxt.text.toString()  // Obtener el texto del password

            if (username == MYUSER && password == MYPASS) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                Toast.makeText(this, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

    }

}