package com.example.dangtime.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FBAuth {
    companion object {
        lateinit var auth: FirebaseAuth

        fun authInit(): FirebaseAuth{
            return Firebase.auth
        }

        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser!!.uid
        }
    }
}