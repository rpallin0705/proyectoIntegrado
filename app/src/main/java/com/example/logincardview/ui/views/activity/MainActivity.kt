package com.example.logincardview.ui.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.myToolbar)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(navController.graph, binding.myDrawer)
        setupActionBarWithNavController(navController, appBarConfiguration)


        NavigationUI.setupWithNavController(binding.myNavView, navController)


        binding.myToolbar.setNavigationOnClickListener {
            binding.myDrawer.open()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun getMainScreenBinding(): ActivityMainBinding {
        return binding
    }
}
