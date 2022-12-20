package com.example.dangtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.model.PostVO
import com.example.dangtime.util.FBDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PostAdapter(val context: Context, val postList: ArrayList<PostVO>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPostPic: ImageView
        val tvPostName: TextView
        val imgPostUploadImg: ImageView
        val tvPostContent: TextView
        val imgPostLike: ImageView
        val imgPostReply: ImageView
        val imgPostBookmark: ImageView
        val tvPostAddress: TextView
        val tvPostTime: TextView

        init {
            imgPostPic = itemView.findViewById(R.id.imgPostPic)
            tvPostName = itemView.findViewById(R.id.tvPostName)
            imgPostUploadImg = itemView.findViewById(R.id.imgPostUploadImg)
            tvPostContent = itemView.findViewById(R.id.tvPostContent)
            imgPostLike = itemView.findViewById(R.id.imgPostLike)
            imgPostReply = itemView.findViewById(R.id.imgPostReply)
            imgPostBookmark = itemView.findViewById(R.id.imgPostBookmark)
            tvPostAddress = itemView.findViewById(R.id.tvPostAddress)
            tvPostTime = itemView.findViewById(R.id.tvPostTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.template_post, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 유저 프로필사진
        val userUid = postList[position].userUid
        val postUid = postList[position].postUid
        val storageReferenceProfile = Firebase.storage.reference.child("/userImages/$userUid/photo")
        storageReferenceProfile.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .circleCrop()
                    .into(holder.imgPostPic)
            } else {
            }
        }
        // 유저 아이디, 주소
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("$userUid").child("name").value.toString()
                val address = snapshot.child("$userUid").child("address").value.toString()
                holder.tvPostName.text = name
                holder.tvPostAddress.text = address
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FBDatabase.getUser().addValueEventListener(postListener)

        // 게시글 이미지, 내용, 시간
        val storageReferencePost =
            Firebase.storage.reference.child("/postUploadImages/$postUid/photo")
        storageReferencePost.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.imgPostUploadImg)
            } else {
                holder.imgPostUploadImg.visibility = View.GONE
            }
        }
        holder.tvPostContent.text = postList[position].content
        holder.tvPostTime.text = postList[position].time
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}