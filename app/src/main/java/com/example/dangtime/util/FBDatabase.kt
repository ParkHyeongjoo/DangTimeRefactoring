package com.example.dangtime.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBDatabase {
    companion object {
        val database = Firebase.database

        fun getUser(): DatabaseReference {
            return database.getReference("user")
        }
    }
}