package com.tech.b2simulator.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var appBarConfiguration: AppBarConfiguration? = null
    private val topMenu = setOf(
        R.id.navigation_home,
        R.id.navigation_exam,
        R.id.navigation_setting
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navView = binding.navView
        bottomNavigationView.background = null
//        bottomNavigationView.menu[1].isEnabled = false


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { controller, _, _ ->
            if (topMenu.contains(controller.currentDestination?.id)) {
                navView.visibility = View.VISIBLE
            } else {
                navView.visibility = View.GONE
            }
        }

        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            topMenu
        )
        setupActionBarWithNavController(navController, appBarConfiguration!!)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}