package com.zamora.lab13.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.zamora.lab13.R

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

        val appBarConfig = AppBarConfiguration(setOf(R.id.loginFragment, R.id.charactersListFragment))

        toolBar = findViewById(R.id.toolbar_main_activity)
        toolBar.setupWithNavController(navController, appBarConfig)

        setToolBarListeners()
    }

    private fun setToolBarListeners() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.characterDetailsFragment2 -> {
                    toolBar.visibility = View.VISIBLE
                    toolBar.menu.clear()
                    toolBar.inflateMenu(R.menu.top_bar_menu)
                    toolBar.menu.findItem(R.id.sort_az_button).isVisible = false
                    toolBar.menu.findItem(R.id.sort_za_button).isVisible = false
                    toolBar.menu.findItem(R.id.logout_button).isVisible = false
                }
                R.id.loginFragment -> {
                    toolBar.visibility = View.GONE
                    toolBar.menu.clear()
                }
                R.id.charactersListFragment ->{
                    toolBar.visibility = View.VISIBLE
                    toolBar.menu.clear()
                    toolBar.inflateMenu(R.menu.top_bar_menu)
                    toolBar.menu.findItem(R.id.delete_button).isVisible = false
                }
            }
        }
    }
}