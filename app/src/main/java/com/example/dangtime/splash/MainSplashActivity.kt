package com.example.dangtime.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dangtime.MainActivity
import com.example.dangtime.R

class MainSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_splash)

        Handler().postDelayed({
            // Intent 생성
            val intent = Intent(
                this@MainSplashActivity,
                MainActivity::class.java
            )
            // Intent 실행
            startActivity(intent)
            finish()
        }, 3000)
    }
}