package com.example.dangtime.home.write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R

class Write1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write1)

        val imgWrite1Back = findViewById<ImageView>(R.id.imgWrite1Back)
        imgWrite1Back.setOnClickListener {
            finish()
        }

        val btnMate = findViewById<Button>(R.id.btnMate)
        val btnTalk = findViewById<Button>(R.id.btnTalk)
        btnMate.setOnClickListener {
            val intent = Intent(this@Write1Activity, Write2Activity::class.java)
            intent.putExtra("category", "mate")
            startActivity(intent)
        }
        btnTalk.setOnClickListener {
            val intent = Intent(this@Write1Activity, Write2Activity::class.java)
            intent.putExtra("category", "talk")
            startActivity(intent)
        }
    }
}