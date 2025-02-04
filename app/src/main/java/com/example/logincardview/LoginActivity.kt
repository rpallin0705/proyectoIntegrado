
package com.example.logincardview

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logincardview.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: LoginActivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

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

        /*init()
        checkUserSession()
        start()*/
    }
}

    /*private fun init() {
        this.auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        loadEmail() // Cargar el correo almacenado, si existe
    }

    // Verifica si hay una sesión iniciada y redirige a MainActivityantiguo si es el caso
    private fun checkUserSession() {
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            val intent = Intent(this, MainActivityantiguo::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun start() {
        // Listener para el botón de login o recuperación
        loginActivityBinding.loginButton.setOnClickListener {
            val email = loginActivityBinding.emailEditText.text.toString().trim()

            // Comprueba si estamos en el modo de recuperación de contraseña
            if (loginActivityBinding.loginButton.text == "Recuperar contraseña") {
                if (email.isNotEmpty()) {
                    recoverPassword(email) { result, msg ->
                        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                        if (result) {
                            resetToLoginState()
                        }
                    }
                } else {
                    Toast.makeText(this, "Por favor, introduce tu correo electrónico", Toast.LENGTH_LONG).show()
                }
            } else {
                val pass = loginActivityBinding.passwordEditText.text.toString()
                if (email.isNotEmpty() && pass.isNotEmpty())
                    startLogin(email, pass) { result, msg ->
                        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                        if (result) {
                            intent = Intent(this@LoginActivity, MainActivityantiguo::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                else
                    Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
            }
        }

        loginActivityBinding.resetPasswordText.setOnClickListener {
            toggleToRecoveryState()
        }

        loginActivityBinding.singUpText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun recoverPassword(email: String, onResult: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { taskResetEmail ->
                if (taskResetEmail.isSuccessful) {
                    onResult(true, "Acabamos de enviarte un email con la nueva password")
                } else {
                    var msg = ""
                    try {
                        throw taskResetEmail.exception ?: Exception("Error de reseteo inesperado")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        msg = "El formato del email es incorrecto"
                    } catch (e: Exception) {
                        msg = e.message.toString()
                    }
                    onResult(false, msg)
                }
            }
    }
*/

/*private fun startLogin(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener { taskSignIn ->
                var msg = ""
                if (taskSignIn.isSuccessful) {
                    val possibleUser = auth.currentUser
                    if (possibleUser?.isEmailVerified == true) {
                        saveEmail(user) // Guardar el correo en SharedPreferences
                        val intent = Intent(this, MainActivityantiguo::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        auth.signOut()
                        onResult(false, "Debes verificar tu correo antes de loguearte")
                    }
                } else {
                    try {
                        throw taskSignIn.exception ?: Exception("Error desconocido")
                    } catch (e: FirebaseAuthInvalidUserException) {
                        msg = "El usuario tiene problemas por haberse borrado o deshabilitado"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        msg = if (e.message?.contains("There is no user record corresponding to this identifier") == true) {
                            "El usuario no existe"
                        } else "Contraseña incorrecta o usuario no registrado"
                    } catch (e: Exception) {
                        msg = e.message.toString()
                    }
                    onResult(false, msg)
                }
            }*//*

    }

    private fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("saved_email", email)
        editor.apply()
    }

    private fun loadEmail() {
        val savedEmail = sharedPreferences.getString("saved_email", "")
        if (!savedEmail.isNullOrEmpty()) {
            loginActivityBinding.emailEditText.setText(savedEmail)
        }
    }

    private fun toggleToRecoveryState() {
        loginActivityBinding.passwordEditText.visibility = android.view.View.INVISIBLE
        loginActivityBinding.loginButton.text = "Recuperar contraseña"
        loginActivityBinding.resetPasswordText.text = "Volver al login"
        loginActivityBinding.resetPasswordText.setOnClickListener {
            resetToLoginState()
        }
    }

    private fun resetToLoginState() {
        loginActivityBinding.passwordEditText.visibility = android.view.View.VISIBLE
        loginActivityBinding.loginButton.text = "Login"
        loginActivityBinding.resetPasswordText.text = "Recuperar contraseña"
        loginActivityBinding.resetPasswordText.setOnClickListener {
            toggleToRecoveryState()
        }
    }
}
*/
