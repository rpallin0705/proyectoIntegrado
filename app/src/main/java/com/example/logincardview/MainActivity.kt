package com.example.logincardview

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import com.example.logincardview.databinding.ActivityMainScreenBinding
import com.example.logincardview.ui.LocalFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mainScreenBinding: ActivityMainScreenBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainScreenBinding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(mainScreenBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()

        loadEmail()  // Cargar el email en el EditText

        if (savedInstanceState == null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, LocalFragment())
            transaction.commit()
        }

        addButtonListeners()
    }

    private fun addButtonListeners() {
        val buttons = listOf(
            mainScreenBinding.btnHome,
            mainScreenBinding.btnAdd,
            mainScreenBinding.btnLogout
        )

        mainScreenBinding.btnAdd.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        mainScreenBinding.btnHome.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        mainScreenBinding.btnLogout.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
            logout()
        }

        updateButtonStates(buttons, mainScreenBinding.btnHome)
    }

    private fun updateButtonStates(buttons: List<ImageButton>, selectedButton: ImageButton) {
        buttons.forEach { button ->
            button.isSelected = (button == selectedButton)
        }
    }

    // Cargar el email guardado en el EditText
    private fun loadEmail() {
        val savedEmail = sharedPreferences.getString("saved_email", "")
        if (!savedEmail.isNullOrEmpty()) {
            mainScreenBinding.emailText.setText(savedEmail)
        }
    }

    // Función para cerrar sesión y borrar SharedPreferences
    private fun logout() {
        auth.signOut()

        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getMainScreenBinding(): ActivityMainScreenBinding {
        return this.mainScreenBinding
    }
}
