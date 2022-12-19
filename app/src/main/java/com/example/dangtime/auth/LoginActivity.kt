package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dangtime.R
import com.example.dangtime.splash.LoginSplashActivity
import com.example.dangtime.util.FBAuth
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
            val email = etLoginEmail.text.toString().trim()
            val pw = etLoginPw.text.toString().trim()
            if (email == "") {
                Toast.makeText(this@LoginActivity, "email을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (pw == "") {
                    Toast.makeText(this@LoginActivity, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    FBAuth.authInit().signInWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(this@LoginActivity) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this@LoginActivity, LoginSplashActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "이메일, 비밀번호를 확인해주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}