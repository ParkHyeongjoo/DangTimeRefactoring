package com.example.dangtime.home.write

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dangtime.R
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBDatabase
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class Write2Activity : AppCompatActivity() {

    private var imageUri: Uri? = null
    lateinit var imgWriteUploadImg: ImageView

    //이미지 등록
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                imageUri = result.data?.data
                imgWriteUploadImg.setImageURI(imageUri)
            } else {
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write2)

        val imgWrite2Back = findViewById<ImageView>(R.id.imgWrite2Back)
        imgWrite2Back.setOnClickListener {
            finish()
        }

        val uid = FBAuth.getUid()
        val category = intent.getStringExtra("category")
        val tvWriteTitle = findViewById<TextView>(R.id.tvWriteTitle)
        if (category == "mate") {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("$uid").child("name").value.toString()
                    tvWriteTitle.text = "${name}에게\n산책 메이트를 선물해주세요"
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
            FBDatabase.getUser().addValueEventListener(postListener)
        } else {
            tvWriteTitle.text = "댕댕이들이랑\n복작복작 수다떨기!"
        }

        imgWriteUploadImg = findViewById(R.id.imgWriteUploadImg)
        imgWriteUploadImg.setOnClickListener {
            val intentImage = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            getContent.launch(intentImage)
        }
        val etWriteContent = findViewById<TextInputEditText>(R.id.etWriteContent)
        val btnWrite = findViewById<Button>(R.id.btnWrite)
        btnWrite.setOnClickListener {
            val content = etWriteContent.text.toString()
            if (imageUri == null){
                Toast.makeText(this@Write2Activity, "사진을 등록해 주세요", Toast.LENGTH_SHORT).show()
            }else{
                if(content == ""){
                    Toast.makeText(this@Write2Activity, "게시글을 작성해 주세요", Toast.LENGTH_SHORT).show()
                }else{

                }
            }
        }
    }
}