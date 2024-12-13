package com.example.logincardview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logincardview.databinding.LoginActivityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: LoginActivityBinding
    private lateinit var auth : FirebaseAuth

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

        init()
        start()
    }

    private fun init() {
        this.auth = Firebase.auth
    }

    private fun start() {

        loginActivityBinding.loginButton.setOnClickListener {
            val email = loginActivityBinding.emailEditText.text.toString().trim()
            val pass = loginActivityBinding.passwordEditText.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty())
                startLogin(email, pass){
                        result, msg ->
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                    if (result){
                        intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            else
                Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
        }

        loginActivityBinding.singUpText.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
        }

    }

    private fun recoverPassword(email : String, onResult: (Boolean, String)->Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{
                    taskResetEmail ->
                if (taskResetEmail.isSuccessful){
                    onResult (true, "Acabamos de enviarte un email con la nueva password")
                }else{
                    var msg = ""
                    try{
                        throw taskResetEmail.exception?:Exception("Error de reseteo inesperado")
                    }catch (e : FirebaseAuthInvalidCredentialsException){
                        msg = "El formato del email es incorrecto"
                    }catch (e: Exception){
                        msg = e.message.toString()
                    }
                    onResult(false, msg)


                }
            }
    }

    private fun startLogin(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener {
                    taskAssin ->
                var msg = ""
                if (taskAssin.isSuccessful){
                    //debemos comprobar si el usuario ha verificado el email
                    val posibleUser = auth.currentUser
                    if (posibleUser?.isEmailVerified == true){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        auth.signOut() //hay que desloguearse, porque no ha verificado.
                        onResult (false, "Debes verificar tu correo antes de loguearte")
                    }
                }else{
                    try {
                        throw taskAssin.exception?: Exception("Error desconocido")
                    }catch (e: FirebaseAuthInvalidUserException){
                        msg = "El usuario tiene problemas por haberse borrado o desabilitado"
                    }catch (e: FirebaseAuthInvalidCredentialsException){
                        msg = if (e.message?.contains("There is no user record corresponding to this identifier") == true){
                            "El usuario no existe"
                        }else "contraseña incorrecta"

                    }catch (e: Exception){
                        msg = e.message.toString()
                    }

                    onResult (false, msg)  //genérico.
                }

            }
    }

}