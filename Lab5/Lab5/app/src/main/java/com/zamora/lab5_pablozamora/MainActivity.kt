package com.zamora.lab5_pablozamora

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.zamora.lab5_pablozamora.data.Restaurant

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var downloadUpdate: TextView
    private lateinit var btnDirections: ImageView
    private lateinit var btnDetails: Button
    private lateinit var restaurantName: TextView
    private lateinit var restaurantAddress: TextView
    private lateinit var restaurantOfficeHours: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.start_button)
        downloadUpdate = findViewById(R.id.downloadUpdate)
        btnDirections = findViewById(R.id.image_directions)
        btnDetails = findViewById(R.id.details_button)
        restaurantName = findViewById(R.id.restaurant_name)
        restaurantAddress = findViewById(R.id.restaurant_address)
        restaurantOfficeHours = findViewById(R.id.restaurant_office_hours)

        initListeners()
    }

    private fun initListeners(){
        btnStart.setOnClickListener{
            Toast.makeText(this, "Pablo Andrés Zamora Vásquez", Toast.LENGTH_LONG).show()
        }

        downloadUpdate.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=es"))
            startActivity(intent)
        }

        btnDirections.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.gt/maps/place/Buta+Ramen+House/@14.5942321,-90.5122543,17z/data=!3m1!4b1!4m5!3m4!1s0x8589a3a9950a26cb:0x1d8d977121b50c6a!8m2!3d14.5942091!4d-90.5100165?hl=es"))
            startActivity(intent)
        }

        btnDetails.setOnClickListener{
            val restaurant = Restaurant(
                name = restaurantName.text.toString(),
                address = restaurantAddress.text.toString(),
                office_hours = restaurantOfficeHours.text.toString(),
                phone_number = "4058 7520"
            )

            val intent = Intent(this,DetailsActivity::class.java)
            intent.putExtra("EXTRA_RESTAURANT", restaurant)
            startActivity(intent)
        }
    }
}