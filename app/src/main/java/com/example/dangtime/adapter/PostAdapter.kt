package com.example.dangtime.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.model.PostVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBDatabase
import com.example.dangtime.util.Time
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
        val tvPostAddress: TextView
        val imgPostUploadImg: ImageView
        val tvPostContent: TextView
        val tvPostTime: TextView
        val imgPostLike: ImageView
        var likeCheck: Boolean
        val tvPostLike: TextView

        val imgPostReply: ImageView
        val tvPostReply: TextView
        val imgPostBookmark: ImageView
        val imgPostShare: ImageView
        val imgPostMore: ImageView

        init {
            imgPostPic = itemView.findViewById(R.id.imgPostPic)
            tvPostName = itemView.findViewById(R.id.tvPostName)
            tvPostAddress = itemView.findViewById(R.id.tvPostAddress)
            imgPostUploadImg = itemView.findViewById(R.id.imgPostUploadImg)
            tvPostContent = itemView.findViewById(R.id.tvPostContent)
            tvPostTime = itemView.findViewById(R.id.tvPostTime)
            imgPostLike = itemView.findViewById(R.id.imgPostLike)
            likeCheck = false
            tvPostLike = itemView.findViewById(R.id.tvPostLike)

            imgPostReply = itemView.findViewById(R.id.imgPostReply)
            tvPostReply = itemView.findViewById(R.id.tvPostReply)
            imgPostBookmark = itemView.findViewById(R.id.imgPostBookmark)
            imgPostShare = itemView.findViewById(R.id.imgPostShare)
            imgPostMore = itemView.findViewById(R.id.imgPostMore)
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
        FBDatabase.getUserRef().addValueEventListener(postListener)

        // 게시글 이미지, 내용, 시간
        val postUid = postList[position].postUid
        val storageReferencePost =
            Firebase.storage.reference.child("/postImages/$postUid/photo")
        storageReferencePost.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.imgPostUploadImg)
            } else {
            }
        }
        holder.tvPostContent.text = postList[position].content
        var time = postList[position].time
        val timeY = time?.substring(0, 4)
        val timeM = time?.substring(5, 7)
        val timeD = time?.substring(8, 10)
        val timeH = time?.substring(11, 13)
        val timem = time?.substring(14, 16)
        val now = Time.getTime()
        val nowY = now.substring(0, 4)
        val nowM = now.substring(5, 7)
        val nowD = now.substring(8, 10)
        val nowH = now.substring(11, 13)
        val nowm = now.substring(14, 16)
        if (nowY.equals(timeY)) {
            if (nowM.equals(timeM)) {
                if (nowD.equals(timeD)) {
                    if (nowH.equals(timeH)) {
                        if ((nowm.toInt() - timem!!.toInt()) > 2) {
                            time = "방금전"
                        } else {
                            time = "${nowm.toInt() - timem!!.toInt()}분전"
                        }
                    } else {
                        time = "${timeH}:${timem}"
                    }
                } else {
                    if ((nowD.toInt() - timeD!!.toInt()) > 1) {
                        time = "${timeM}월 ${timeD}일"
                    } else {
                        time = "어제"
                    }
                }
            } else {
                time = "${timeM}월 ${timeD}일"
            }
        } else {
            time = "${timeY}.${timeM}.${timeD}"
        }
        holder.tvPostTime.text = time

        // 좋아요
        val uid = FBAuth.getUid()
        val likeList = ArrayList<String>()
        holder.imgPostLike.setOnClickListener {
            if (!holder.likeCheck) {
                FBDatabase.getPostRef().child(postUid.toString()).child("like").child(uid)
                    .setValue(uid)
            } else {
                FBDatabase.getPostRef().child(postUid.toString()).child("like").child(uid)
                    .removeValue()
                holder.likeCheck = false
            }
        }
        val likeListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likeList.clear()
                for (model in snapshot.children) {
                    val likeData = model.value.toString()
                    likeList.add(likeData)
                }
                for (i in 0 until likeList.size) {
                    if (likeList[i] == uid) {
                        holder.likeCheck = true
                        break;
                    } else {
                        holder.likeCheck = false
                    }
                }
                if (likeList.size == 0) {
                    holder.tvPostLike.visibility = View.GONE
                } else {
                    holder.tvPostLike.visibility = View.VISIBLE
                    holder.tvPostLike.text = "좋아요 ${likeList.size}개"
                }
                if (holder.likeCheck) {
                    holder.imgPostLike.setImageResource(R.drawable.heart_full)
                } else {
                    holder.imgPostLike.setImageResource(R.drawable.heart_empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FBDatabase.getPostRef().child(postUid.toString()).child("like")
            .addValueEventListener(likeListener)

        // 댓글


    }

    override fun getItemCount(): Int {
        return postList.size
    }
}