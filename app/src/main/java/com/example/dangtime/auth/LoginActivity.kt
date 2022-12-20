package com.example.dangtime.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dangtime.R
import com.example.dangtime.splash.LoginSplashActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.LocationPermissionActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    lateinit var etLoginEmail: TextInputEditText
    lateinit var etLoginPw: TextInputEditText
    lateinit var autoSp: SharedPreferences
    lateinit var cbEmail: CheckBox
    lateinit var cbAutoLogin: CheckBox
    lateinit var autoLoginEmail: String
    lateinit var autoLoginPw: String
    lateinit var cbEmailCheck: String
    lateinit var cbAutoLoginCheck: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPw = findViewById(R.id.etLoginPw)

        cbEmail = findViewById(R.id.cbEmail)
        cbAutoLogin = findViewById(R.id.cbAutoLogin)
        autoSp = getSharedPreferences("autoLoginInfo", Context.MODE_PRIVATE)
        autoLoginEmail = autoSp.getString("loginEmail", "").toString()
        autoLoginPw = autoSp.getString("loginPw", "").toString()
        cbEmailCheck = autoSp.getString("cbEmailCheck", "").toString()
        cbAutoLoginCheck = autoSp.getString("cbAutoLoginCheck", "").toString()

        checkAutoEmail(autoLoginEmail)
        checkAutoLogin(autoLoginEmail, autoLoginPw)


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
        val btnLoginLogin = findViewById<Button>(R.id.btnLoginLogin)
        btnLoginLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
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
                            val editor = autoSp.edit()
                            Log.d("checkbox login", cbEmail.isChecked.toString())
                            if (cbEmail.isChecked) {
                                // commit 꼭 하기!!!!!!
                                editor.putString("loginEmail", email)
                                editor.putString("cbEmailCheck", "check")
                                editor.commit()
                            } else {
                                editor.putString("loginEmail", "")
                                editor.putString("cbEmailCheck", "")
                                editor.commit()
                            }
                            if (cbAutoLogin.isChecked) {
                                editor.putString("loginEmail", email)
                                editor.putString("loginPw", pw)
                                editor.putString("cbAutoLoginCheck", "check")
                                editor.commit()
                            } else if(cbEmail.isChecked && !cbAutoLogin.isChecked) {
                                editor.putString("loginPw", "")
                                editor.putString("cbAutoLoginCheck", "")
                                editor.commit()
                            }else{
                                editor.putString("loginEmail", "")
                                editor.putString("loginPw", "")
                                editor.putString("cbAutoLoginCheck", "")
                                editor.commit()
                            }
                            val intent =
                                Intent(this@LoginActivity, LoginSplashActivity::class.java)
                            startActivity(intent)
                            finish()
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

    private fun checkAutoEmail(autoLoginEmail: String) {
        if (cbEmailCheck == "check") {
            cbEmail.isChecked = true
            etLoginEmail.setText(autoLoginEmail)
        }
    }

    private fun checkAutoLogin(autoLoginEmail: String, autoLoginPw: String) {
        if (cbAutoLoginCheck == "check") {
            cbEmail.isChecked = true
            cbAutoLogin.isChecked = true
            etLoginEmail.setText(autoLoginEmail)
            etLoginPw.setText(autoLoginPw)
            login()
        }
    }
}