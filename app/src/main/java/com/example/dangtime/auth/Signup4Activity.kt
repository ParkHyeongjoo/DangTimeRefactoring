package com.example.dangtime.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.dangtime.R
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView

class Signup4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup4)

        val imgSignup4Back = findViewById<ImageView>(R.id.imgSignup4Back)
        val imgSignupAddPic = findViewById<CircleImageView>(R.id.imgSignupAddPic)
        val llSignupPhoto = findViewById<LinearLayout>(R.id.llSignupPhoto)
        val btnSignupGallery = findViewById<Button>(R.id.btnSignupGallery)
        val btnSignupCamera =  findViewById<Button>(R.id.btnSignupCamera)
        val etSignupDogName = findViewById<TextInputEditText>(R.id.etSignupDogName)
        val etSignupDogNick = findViewById<TextInputEditText>(R.id.etSignupDogNick)
        val btnSignupEnd = findViewById<Button>(R.id.btnSignupEnd)


    }
}