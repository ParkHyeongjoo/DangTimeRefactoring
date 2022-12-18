package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dangtime.R
import com.example.dangtime.util.WebSearchActivity

class Signup2InputActivity : AppCompatActivity() {

    lateinit var etSignup2Location: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2_input)

        // 뒤로가기
        val imgSingup2Back = findViewById<ImageView>(R.id.imgSignup2Back)
        imgSingup2Back.setOnClickListener {
            finish()
        }

        // 주소 검색
        etSignup2Location = findViewById(R.id.etSignup2Location)
        val imgSignup2Location = findViewById<ImageView>(R.id.imgSignup2Location)
        etSignup2Location.isFocusable = false
        etSignup2Location.setOnClickListener {
            val intent = Intent(this@Signup2InputActivity, WebSearchActivity::class.java)
            launcher.launch(intent)
        }
        imgSignup2Location.setOnClickListener {
            val intent = Intent(this@Signup2InputActivity, WebSearchActivity::class.java)
            launcher.launch(intent)
        }

        // 다음 Activity
        val btnSignup2Next = findViewById<Button>(R.id.btnSignup2Next)
        btnSignup2Next.setOnClickListener {
            val intent = Intent(this@Signup2InputActivity, Signup3Activity::class.java)
            intent.putExtra("address", etSignup2Location.text.toString())
            startActivity(intent)
        }
    }

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            if (it.data != null) {
                val data: String = it.data!!.getStringExtra("data") as String
                etSignup2Location.setText(data)
            }
        }
    }
}