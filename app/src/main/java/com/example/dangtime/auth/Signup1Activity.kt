package com.example.dangtime.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.dangtime.R
import com.example.dangtime.util.MapActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class Signup1Activity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val multiplePermissionsCode = 100
    var address = ArrayList<String>()
    var lat: Double = 0.0
    var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup1)

        // 뒤로가기
        val imgSingup1Back = findViewById<ImageView>(R.id.imgSingup1Back)
        imgSingup1Back.setOnClickListener {
            finish()
        }

        // 위치 검색 Activity 로 이동
        val btnSignupLocationInput = findViewById<Button>(R.id.btnSignupLocationInput)
        btnSignupLocationInput.setOnClickListener {
            val intent = Intent(this@Signup1Activity, Signup2InputActivity::class.java)
            startActivity(intent)
        }

        // GPS 위치 자동검색 후 이동
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@Signup1Activity);
        val btnSignupLocationAuto = findViewById<Button>(R.id.btnSignupLocationAuto)
        btnSignupLocationAuto.setOnClickListener {
            checkLocationPermission()
        }
    }

    //    권한 확인 후 GPS 현재 위치 검색
    @SuppressLint("MissingPermission")
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    var geocoder = Geocoder(this, Locale.KOREA)
                    if (location != null) {
                        val addrList =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        for (addr in addrList) {
                            val splitedAddr = addr.getAddressLine(0).split(" ")
                            address = splitedAddr as ArrayList<String>
                        }
//                        Toast.makeText(this, "$address", Toast.LENGTH_SHORT).show()
                        lat = location.latitude
                        long = location.longitude

                        val intent = Intent(this@Signup1Activity, MapActivity::class.java)
                        intent.putExtra("lat", lat.toString())
                        intent.putExtra("long", long.toString())
//                        주소 동단위로 자르기
                        intent.putExtra("address", address.toString())
                        Log.d("address", address.toString())
                        startActivity(intent)
                    }
                }
        } else {
            Toast.makeText(this, "위치권한이 없습니다..", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            multiplePermissionsCode -> {
                if (grantResults.isNotEmpty()) {
                    for ((i, permission) in permissions.withIndex()) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 획득 실패시 동작
                            Toast.makeText(
                                this,
                                "The user has denied to $permission",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i("TAG", "I can't work for you anymore then. ByeBye!")
                        } else {
                            var gpsGranted = true
                        }
                    }
                }
            }
        }
    }
}