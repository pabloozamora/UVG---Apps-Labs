package com.zamora.lab4_pablozamora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.start_button)

        initListeners()
    }

    private fun initListeners(){
        btnStart.setOnClickListener{
            Toast.makeText(this, "Pablo Andrés Zamora Vásquez", Toast.LENGTH_LONG).show()
        }
    }
}