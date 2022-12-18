package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.util.LocationPermissionActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 뒤로가기 (mainActivity로 이동)
        val imgLoginBack = findViewById<ImageView>(R.id.imgLoginBack)
        imgLoginBack.setOnClickListener {
            finish()
        }

        // 회원가입 화면으로 이동
        val imgLoginSignup = findViewById<ImageView>(R.id.imgLoginSignup)
        val tvLoginSignup = findViewById<TextView>(R.id.tvLoginSignup)
        imgLoginSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, LocationPermissionActivity::class.java)
            startActivity(intent)
        }
        tvLoginSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, LocationPermissionActivity::class.java)
            startActivity(intent)
        }

        // Login 이후 post로 이동
        val etLoginEmail = findViewById<TextInputEditText>(R.id.etLoginEmail)
        val etLoginPw = findViewById<TextInputEditText>(R.id.etLoginPw)
        val btnLoginLogin = findViewById<Button>(R.id.btnLoginLogin)
        btnLoginLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val pw = etLoginPw.text.toString()
        }


    }
}