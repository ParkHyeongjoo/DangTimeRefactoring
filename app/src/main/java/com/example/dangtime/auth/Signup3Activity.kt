package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R
import com.google.android.material.textfield.TextInputEditText

class Signup3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup3)

        // 뒤로가기
        val imgSignup3Back = findViewById<ImageView>(R.id.imgSignup3Back)
        imgSignup3Back.setOnClickListener {
            finish()
        }

        val etSignupEmail = findViewById<TextInputEditText>(R.id.etSignupEmail)
        val etSignupPw = findViewById<TextInputEditText>(R.id.etSignupPw)
        val etSignupPwRe = findViewById<TextInputEditText>(R.id.etSignupPwRe)



        // 다음 Activity
        val btnSignup3Next = findViewById<Button>(R.id.btnSignup3Next)
        btnSignup3Next.setOnClickListener {
            val intent = Intent(this@Signup3Activity, Signup4Activity::class.java)

        }
    }
}