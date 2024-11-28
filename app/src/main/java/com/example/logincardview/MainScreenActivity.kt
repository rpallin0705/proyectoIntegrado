package com.example.logincardview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.controller.Controller
import com.example.logincardview.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    private lateinit var mainScreenBinding: ActivityMainScreenBinding
    lateinit var  controller: Controller


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainScreenBinding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(mainScreenBinding.root)



        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        mainScreenBinding.userInfo.text = "Usuario: ${username} - Contraseña: ${password}"

        init()
    }

    fun getBinding(): ActivityMainScreenBinding {
        return mainScreenBinding
    }

    fun init(){
        initRecyclerView()
        controller = Controller(this)
        controller.setAdapter()
    }

    private fun initRecyclerView() {
        mainScreenBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}