package com.example.dangtime.home.post.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.adapter.PostAdapter
import com.example.dangtime.model.PostVO
import com.example.dangtime.util.FBDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Home1AllFragment : Fragment() {

    // item
    var postList = ArrayList<PostVO>()
    lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home1_all, container, false)

        // container
        val rvPostHomeAll = view.findViewById<RecyclerView>(R.id.rvPostHomeAll)

        // template
        // template_post

        getPost()

        // adapter
        adapter = PostAdapter(
            requireContext(),
            postList
        )

        // adapter container 연결
        rvPostHomeAll.adapter = adapter
        rvPostHomeAll.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    // post 가져오기
    fun getPost() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (model in snapshot.children) {
                    val postData = model.child("write").getValue(PostVO::class.java)
                    if (postData != null) {
                        postList.add(postData)
                    }
                }
                postList.reverse()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FBDatabase.getPostRef().addValueEventListener(postListener)

    }
}