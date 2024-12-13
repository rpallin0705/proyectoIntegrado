package com.example.logincardview

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import com.example.logincardview.databinding.ActivityMainScreenBinding
import com.example.logincardview.ui.LocalFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainScreenBinding: ActivityMainScreenBinding

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
            mainScreenBinding.btnFavorites
        )


        mainScreenBinding.btnAdd.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        mainScreenBinding.btnHome.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        mainScreenBinding.btnFavorites.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        updateButtonStates(buttons, mainScreenBinding.btnHome)
    }

     fun updateButtonStates(buttons: List<ImageButton>, selectedButton: ImageButton) {
        buttons.forEach { button ->
            // Establecer el estado seleccionado solo en el botón presionado
            button.isSelected = (button == selectedButton)
        }
    }

    fun getMainScreenBinding() : ActivityMainScreenBinding {
        return this.mainScreenBinding
    }

}
