package com.example.logincardview

import AddLocalDFragment
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
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
            val fm : FragmentManager = supportFragmentManager
            val addLocalDFragment = AddLocalDFragment()
            addLocalDFragment.show(fm, "add")


        }

        mainScreenBinding.btnHome.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        mainScreenBinding.btnFavorites.setOnClickListener {
            updateButtonStates(buttons, it as ImageButton)
        }

        updateButtonStates(buttons, mainScreenBinding.btnHome)
    }

    private fun updateButtonStates(buttons: List<ImageButton>, selectedButton: ImageButton) {
        buttons.forEach { button ->
            // Establecer el estado seleccionado solo en el botón presionado
            button.isSelected = (button == selectedButton)
        }
    }

}
