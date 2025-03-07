package com.example.logincardview.ui.views.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.logincardview.LoginActivity
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityMainBinding
import com.example.logincardview.network.RetrofitClient
import com.example.logincardview.ui.modelview.AuthViewModel
import com.example.logincardview.utils.MySharedPreferences
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = AuthViewModel(this)
        MySharedPreferences.init(this)

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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.localFragment), binding.myDrawer)
        setupActionBarWithNavController(navController, appBarConfiguration)

        NavigationUI.setupWithNavController(binding.myNavView, navController)

        // Escuchar cambios de fragmento para actualizar el título manualmente
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appBarMain.myToolbar.findViewById<TextView>(R.id.toolbar_title).text =
                destination.label
        }

        binding.appBarMain.myToolbar.setNavigationOnClickListener {
            binding.myDrawer.open()
        }


        binding.myNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    authViewModel.logout(
                        onLogoutComplete = {
                            Toast.makeText(this@MainActivity, "Logout exitoso", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@MainActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        },
                        onError = { message ->
                            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                        }
                    )
                    true
                }
                else -> {
                    val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                    binding.myDrawer.closeDrawers()
                    handled
                }
            }
        }
    }

    private suspend fun logoutUser() {
        val sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            try {
                val response = RetrofitClient.authApi.logout("Bearer $token")
                if (response.isSuccessful) {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Error al cerrar sesión", Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this@MainActivity, "Token no encontrado", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    fun getMainScreenBinding(): ActivityMainBinding {
        return binding
    }
}
