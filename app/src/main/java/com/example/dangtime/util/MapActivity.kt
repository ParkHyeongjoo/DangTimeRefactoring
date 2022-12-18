package com.example.dangtime.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.auth.Signup3Activity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var address : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapActivity)

        val btnAutoLocation = findViewById<Button>(R.id.btnAutoLocation)
        val tvMapLocation = findViewById<TextView>(R.id.tvMapLocation)

        address = intent.getStringExtra("address").toString()
        tvMapLocation.setText(address)
        Log.d("address", address)

        btnAutoLocation.setOnClickListener {
            val intent = Intent(this, Signup3Activity::class.java)
            intent.putExtra("address", address)
            intent.putExtra("auto", "auto")
            startActivity(intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lat = intent.getStringExtra("lat").toString()
        val long = intent.getStringExtra("long").toString()
        address = intent.getStringExtra("address").toString()

        // Add a marker in Sydney and move the camera
        val smhrd = LatLng(lat.toDouble(), long.toDouble())
        mMap.addMarker(
            MarkerOptions()
                .position(smhrd)
                .title(address)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(smhrd))
        mMap.setMinZoomPreference(15.0f)
    }
}