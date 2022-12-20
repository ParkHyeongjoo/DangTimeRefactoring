package com.example.dangtime.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LoginSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_splash)

        val uid = FBAuth.getUid()
        val userRef = FBDatabase.getUser()
        val tvSplashName = findViewById<TextView>(R.id.tvSplashName)

        val postListener = object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("$uid").child("name").value.toString()
                tvSplashName.text = "$name 에게"
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        userRef.addValueEventListener(postListener)

        Handler().postDelayed({
            val intent = Intent(this@LoginSplashActivity,
                HomeActivity::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}