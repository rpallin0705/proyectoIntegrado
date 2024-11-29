package com.example.logincardview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.logincardview.databinding.ActivityMainScreenBinding
import com.example.logincardview.ui.LocalFragment

class MainScreenActivity : AppCompatActivity() {

    private lateinit var mainScreenBinding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainScreenBinding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(mainScreenBinding.root)


        if (savedInstanceState == null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, LocalFragment())
            transaction.commit()
        }

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        mainScreenBinding.userInfo.text = "Usuario: ${username} - Contraseña: ${password}"
    }

}
