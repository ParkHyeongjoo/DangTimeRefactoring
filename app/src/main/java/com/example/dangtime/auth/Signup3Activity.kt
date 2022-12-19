package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dangtime.R
import com.example.dangtime.util.FBAuth
import com.google.android.material.textfield.TextInputEditText

class Signup3Activity : AppCompatActivity() {

    // 정규식검사
    private var emailFlag = false
    private var pwFlag = false
    private var pwReFlag = false

    lateinit var trimAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup3)

        val address = intent.getStringExtra("address")
        val tvSignup3Address = findViewById<TextView>(R.id.tvSignup3Address)

        val splitLocation = address!!.split(" ").asReversed()

        if (intent.getStringExtra("auto") == "auto") {
            trimAddress = splitLocation[1].substring(0, splitLocation[1].length - 1)
        } else {
            trimAddress = splitLocation[0].substring(
                1,
                splitLocation[0].length - 1
            )
        }
        tvSignup3Address.text = trimAddress

        // 뒤로가기
        val imgSignup3Back = findViewById<ImageView>(R.id.imgSignup3Back)
        imgSignup3Back.setOnClickListener {
            finish()
        }

        val etSignupEmail = findViewById<TextInputEditText>(R.id.etSignupEmail)
        val etSignupPw = findViewById<TextInputEditText>(R.id.etSignupPw)
        val etSignupPwRe = findViewById<TextInputEditText>(R.id.etSignupPwRe)
        val tvEmailCheck = findViewById<TextView>(R.id.tvEmailCheck)
        val tvPwCheck = findViewById<TextView>(R.id.tvPwCheck)
        val tvPwReCheck = findViewById<TextView>(R.id.tvPwReCheck)

        // 정규식 검사
        etSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val emailRegex =
                    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
                val email = etSignupEmail.text.toString().trim()
                if (email.matches(emailRegex)) {
                    tvEmailCheck.visibility = View.INVISIBLE
                    emailFlag = true
                } else {
                    tvEmailCheck.visibility = View.VISIBLE
                    emailFlag = false
                }
            }
        })
        etSignupPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val pwRegex =
                    "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,}$".toRegex()
                val pw = etSignupPw.text.toString().trim()
                if (pw.matches(pwRegex)) {
                    tvPwCheck.visibility = View.INVISIBLE
                    pwFlag = true
                } else {
                    tvPwCheck.visibility = View.VISIBLE
                    pwFlag = false
                }
            }
        })
        etSignupPwRe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val pw = etSignupPw.text.toString().trim()
                val pwCheck = etSignupPwRe.text.toString().trim()
                if (pwCheck == pw) {
                    tvPwReCheck.visibility = View.INVISIBLE
                    pwReFlag = true
                } else {
                    tvPwReCheck.visibility = View.VISIBLE
                    pwReFlag = false
                }
            }
        })


        // 다음 Activity
        val btnSignup3Next = findViewById<Button>(R.id.btnSignup3Next)
        btnSignup3Next.setOnClickListener {
            val email = etSignupEmail.text.toString().trim()
            val pw = etSignupPw.text.toString().trim()
            if (email == "") {
                Toast.makeText(this@Signup3Activity, "email을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (pw == "") {
                    Toast.makeText(this@Signup3Activity, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    if (etSignupPwRe.text.toString() == "") {
                        Toast.makeText(this@Signup3Activity, "비밀번호 재입력을 입력해주세요", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (emailFlag && pwFlag && pwReFlag) {
                            FBAuth.authInit().signInWithEmailAndPassword(email, pw)
                                .addOnCompleteListener(this@Signup3Activity) { task ->
                                    if (!task.isSuccessful) {
                                        val intent = Intent(
                                            this@Signup3Activity,
                                            Signup4Activity::class.java
                                        )
                                        intent.putExtra("address", trimAddress)
                                        intent.putExtra("email", email)
                                        intent.putExtra("pw", pw)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this@Signup3Activity,
                                            "이미 사용중인 email입니다",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}