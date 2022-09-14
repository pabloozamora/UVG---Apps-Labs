package com.zamora.lab9.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.zamora.lab9.R

class MainActivity : AppCompatActivity() {

    private lateinit var toolBar: MaterialToolbar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Vincular comportamiento de toolbar a JetPack
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container_main_activity
        ) as NavHostFragment


        navController = navHostFragment.navController

        val appBarConfig = AppBarConfiguration(navController.graph)

        toolBar = findViewById(R.id.toolbar_main_activity)
        toolBar.setupWithNavController(navController, appBarConfig)

        setToolBarListeners()
    }

    private fun setToolBarListeners() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.characterDetailsFragment2 -> {
                    toolBar.menu.clear()
                }
                else-> {
                    toolBar.menu.clear()
                    toolBar.inflateMenu(R.menu.top_bar_menu)
                }
            }
        }
    }
}