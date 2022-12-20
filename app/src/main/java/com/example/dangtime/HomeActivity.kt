package com.example.dangtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.home.post.bookmark.BookmarkFragment
import com.example.dangtime.home.post.home.HomeFragment
import com.example.dangtime.home.post.mypost.MypostFragment
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val uid = FBAuth.getUid()

        val tvHomeTitle = findViewById<TextView>(R.id.tvHomeTitle)
        val bnvHome = findViewById<BottomNavigationView>(R.id.bnvHome)

        // 프로필 사진 가져오기
        val imgHomePic = findViewById<ImageView>(R.id.imgHomePic)
        val storageReference = Firebase.storage.reference.child("/userImages/$uid/photo")
        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this@HomeActivity)
                    .load(task.result)
                    .circleCrop()
                    .into(imgHomePic)
            } else {
            }
        }

        // 시작시 2번 fragment 으로 시작
        supportFragmentManager.beginTransaction().replace(
            R.id.flHome,
            HomeFragment()
        ).commit()
        tvHomeTitle.text = "댕댕이 모여라"
        bnvHome.selectedItemId = R.id.tabHome2

        bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tabHome1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        BookmarkFragment()
                    ).commit()
                    tvHomeTitle.text = "찜"
                }
                R.id.tabHome2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        HomeFragment()
                    ).commit()
                    tvHomeTitle.text = "댕댕이 모여라"
                }
                R.id.tabHome3 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        MypostFragment()
                    ).commit()
                    tvHomeTitle.text = "내가 쓴 글"
                }
            }
            true
        }

        val imgHomeChat = findViewById<ImageView>(R.id.imgHomeChat)
        imgHomeChat.setOnClickListener {

        }
    }
}