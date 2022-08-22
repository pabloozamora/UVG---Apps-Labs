package com.zamora.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        bottomNav = findViewById(R.id.bottomNavActivity)
        setFragment(HomeFragment())
        initListeners()
    }

    private fun initListeners(){
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_option_home -> setFragment(HomeFragment())
                R.id.menu_option_search -> setFragment(SearchFragment())
                R.id.menu_option_library -> setFragment(LibraryFragment())
        }
            true

        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(R.id.fragment_container_bottomNavActivity, fragment)
        }
    }
}