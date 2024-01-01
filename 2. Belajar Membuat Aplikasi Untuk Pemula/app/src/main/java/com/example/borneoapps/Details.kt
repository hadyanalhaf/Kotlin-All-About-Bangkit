package com.example.borneoapps

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Details : AppCompatActivity() {

    companion object{
        const val EXTRA_TRIP = "extra_trip"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val imgDetails:ImageView = findViewById(R.id.img_details)
        val nameDetails:TextView = findViewById(R.id.title_details)
        val descDetails:TextView = findViewById(R.id.desc_details)
        val locationDetails:TextView =findViewById(R.id.locations)

        val tripdet = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Trip>(EXTRA_TRIP, Trip::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Trip>(EXTRA_TRIP)
        }

        if (tripdet != null) {
            val imgd = tripdet.photo
            val titled =tripdet.name.toString()
            val descd =tripdet.description.toString()
            val locd =tripdet.location.toString()
            imgDetails.setImageResource(imgd)
            nameDetails.text = titled
            descDetails.text = descd
            locationDetails.text = locd
        }

    }
}