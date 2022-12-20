package com.example.dangtime.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.dangtime.R
import com.example.dangtime.model.FriendVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBDatabase
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Signup4Activity : AppCompatActivity() {

    private var imageUri: Uri? = null
    lateinit var imgSignupAddPic: CircleImageView

    //이미지 등록
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                imageUri = result.data?.data //이미지 경로 원본
                imgSignupAddPic.setImageURI(imageUri) //이미지 뷰를 바꿈
                Log.d("이미지", "성공")
            } else {
                Log.d("이미지", "실패")
            }
        }

    private var mCurrentPhotoPath: String? = null
    val REQUEST_TAKE_PHOTO = 10
    val REQUEST_PERMISSION = 11
    lateinit var file: File
    var profileCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup4)

        // 카메라, 사진첩 권한 확인
        checkPermission()

        val address = intent.getStringExtra("address")!!
        val email = intent.getStringExtra("email")!!
        val pw = intent.getStringExtra("pw")!!

        val tvSignup4Address = findViewById<TextView>(R.id.tvSignup4Address)
        tvSignup4Address.text = address

        // 뒤로가기
        val imgSignup4Back = findViewById<ImageView>(R.id.imgSignup4Back)
        imgSignup4Back.setOnClickListener {
            finish()
        }
        // 사진추가 드롭박스 레이아웃
        val llSignupPhoto = findViewById<LinearLayout>(R.id.llSignupPhoto)
        llSignupPhoto.visibility = View.GONE

        var photo = false
        imgSignupAddPic = findViewById(R.id.imgSignupAddPic)
        imgSignupAddPic.setOnClickListener {
            if (photo) {
                photo = false
                llSignupPhoto.visibility = View.GONE
            } else {
                photo = true
                llSignupPhoto.visibility = View.VISIBLE
            }
        }

        val btnSignupGallery = findViewById<Button>(R.id.btnSignupGallery)
        btnSignupGallery.setOnClickListener {
            val intentImage = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            getContent.launch(intentImage)
            profileCheck = true

            photo = false
            llSignupPhoto.visibility = View.GONE
        }
        val btnSignupCamera = findViewById<Button>(R.id.btnSignupCamera)
        btnSignupCamera.setOnClickListener {
            captureCamera()

            profileCheck = true
            photo = false
            llSignupPhoto.visibility = View.GONE
        }

        val etSignupDogName = findViewById<TextInputEditText>(R.id.etSignupDogName)
        val etSignupDogNick = findViewById<TextInputEditText>(R.id.etSignupDogNick)

        val btnSignupEnd = findViewById<Button>(R.id.btnSignupEnd)
        btnSignupEnd.setOnClickListener {
            val intent = Intent(this@Signup4Activity, LoginActivity::class.java)
            val dogName = etSignupDogName.text.toString().trim()
            val dogNick = etSignupDogNick.text.toString().trim()
            val name = "$dogNick $dogName"

            if (!profileCheck) {
                Toast.makeText(this, "프로필사진을 등록해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (dogName == "") {
                    Toast.makeText(this, "반려견 이름을 작성해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    if (dogNick == "") {
                        Toast.makeText(this, "반려견 닉네임을 작성해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        FBAuth.authInit().createUserWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(this@Signup4Activity) { task ->
                                if (task.isSuccessful) {
                                    val uid = FBAuth.getUid()
                                    FirebaseStorage.getInstance().reference.child("userImages")
                                        .child("$uid/photo").putFile(imageUri!!)
                                        .addOnCompleteListener {
                                            var userProfile: Uri? = null
                                            FirebaseStorage.getInstance().reference.child("userImages")
                                                .child("$uid/photo").downloadUrl
                                                .addOnSuccessListener {
                                                    userProfile = it
                                                    val friend = FriendVO(
                                                        email,
                                                        name,
                                                        address,
                                                        userProfile.toString(),
                                                        uid
                                                    )
                                                    FBDatabase.getUser().child(uid).setValue(friend)
                                                }
                                        }
                                    Toast.makeText(this@Signup4Activity, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@Signup4Activity,
                                        "회원가입에 실패하였습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            }
        }
    }

    //권한 확인
    fun checkPermission() {
        val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val permissionRead =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionWrite =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED || permissionRead != PackageManager.PERMISSION_GRANTED || permissionWrite != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_PERMISSION
            )
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File? { // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_$timeStamp.jpg"
        var imageFile: File? = null
        val storageDir = File(
            Environment.getExternalStorageDirectory().toString() + "/Pictures",
            "Wimmy"
        )
        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString())
            storageDir.mkdirs()
        }
        imageFile = File(storageDir, imageFileName)
        mCurrentPhotoPath = imageFile.absolutePath
        return imageFile
    }

    private fun captureCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.e("captureCamera Error", ex.toString())
                return
            }
            if (photoFile != null) { // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함
                val providerURI = FileProvider.getUriForFile(this, packageName, photoFile)
                imageUri = providerURI;
                // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == RESULT_OK) {
                    try {
                        galleryAddPic();
                    } catch (e: Exception) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(this@Signup4Activity, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT)
                        .show();
                }
            }
        }
    }

    private fun galleryAddPic() {
        val mediaScanIntent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        val f: File = File(mCurrentPhotoPath);
        val contentUri: Uri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        imgSignupAddPic.setImageURI(imageUri);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
}