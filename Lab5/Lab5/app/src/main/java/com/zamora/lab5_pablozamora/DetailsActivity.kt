package com.zamora.lab5_pablozamora

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.zamora.lab5_pablozamora.data.Restaurant

class DetailsActivity : AppCompatActivity() {

    private lateinit var restaurantName: TextView
    private lateinit var restaurantAddress: TextView
    private lateinit var restaurantOfficeHours: TextView
    private lateinit var startVisit: Button
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var btnCall: TextView
    private lateinit var phoneNum: TextView
    private lateinit var restaurant: Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        restaurant= intent.getSerializableExtra("EXTRA_RESTAURANT") as Restaurant

        rootLayout = findViewById(R.id.rootLayout)
        startVisit = findViewById(R.id.start_button)
        btnCall = findViewById(R.id.call_btn)

        //Nombre del restaurante
        restaurantName = findViewById(R.id.restaurant_name)
        restaurantName.text = restaurant.name

        //Dirección del restaurante
        restaurantAddress = findViewById(R.id.restaurant_address)
        restaurantAddress.text = restaurant.address

        //Horario de atención del restaurante
        restaurantOfficeHours = findViewById(R.id.restaurant_office_hours)
        restaurantOfficeHours.text = restaurant.office_hours

        //Teléfono del restaurante
        phoneNum = findViewById(R.id.phone_num)
        phoneNum.text = restaurant.phone_number

        initListeners()
    }

    private fun initListeners(){

        startVisit.setOnClickListener{
            checkCameraPermission()
        }

        btnCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + restaurant.phone_number))
            startActivity(intent)
        }
    }

    private fun checkCameraPermission(){
        if (!hasCameraPermission()){
            checkRequestRationale(Manifest.permission.CAMERA)
        }
        else{
            Toast.makeText(this, "Permiso otorgado, abrir cámara", Toast.LENGTH_LONG).show()
        }
    }

    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun checkRequestRationale(permission: String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (shouldShowRequestPermissionRationale(permission)){
                Snackbar.make(rootLayout,
                    "El acceso a la cámara es necesario para tomar fotos",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Ok"){
                    ActivityCompat.requestPermissions(this, arrayOf(permission),0)
                }.show()
            } else{
                ActivityCompat.requestPermissions(this, arrayOf(permission),0)
            }
        }
    }
}