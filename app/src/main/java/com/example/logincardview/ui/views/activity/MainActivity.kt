package com.example.logincardview.ui.views.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.logincardview.LoginActivity
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("saved_email", "")
        val recycler = binding.myNavView.getHeaderView(0)
        val emailTextView = recycler.findViewById<TextView>(R.id.txt_email)
        val userTextView = recycler.findViewById<TextView>(R.id.txt_name)
        emailTextView.text = email
        userTextView.text = email?.substringBefore('@')

        setSupportActionBar(binding.appBarMain.myToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Desactivar título predeterminado

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.localFragment), binding.myDrawer)
        setupActionBarWithNavController(navController, appBarConfiguration)

        NavigationUI.setupWithNavController(binding.myNavView, navController)

        // Escuchar cambios de fragmento para actualizar el título manualmente
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appBarMain.myToolbar.findViewById<TextView>(R.id.toolbar_title).text = destination.label
        }

        binding.appBarMain.myToolbar.setNavigationOnClickListener {
            binding.myDrawer.open()
        }


        binding.myNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {

                    val sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()

                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    true  // Consume el clic
                }
                else -> {
                    // Si el clic no es en logout, se comporta normalmente:
                    // Navega al destino seleccionado y cierra el Drawer.
                    val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                    binding.myDrawer.closeDrawers()
                    handled
                }
            }
        }


    }



    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun getMainScreenBinding(): ActivityMainBinding {
        return binding
    }
}
