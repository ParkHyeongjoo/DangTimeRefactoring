package com.example.dangtime.home.post.bookmark

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
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Bookmark1AllFragment : Fragment() {

    // item
    lateinit var adapter: PostAdapter
    var postList = ArrayList<PostVO>()
    var bookmarkList = ArrayList<String>()
    var check = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark1_all, container, false)

        // container
        val rvPostBookmarkAll = view.findViewById<RecyclerView>(R.id.rvPostBookmarkAll)

        // template
        // template_post

        getPost()

        // adapter
        adapter = PostAdapter(
            requireContext(),
            postList
        )

        // adapter container 연결
        rvPostBookmarkAll.adapter = adapter
        rvPostBookmarkAll.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    // post 가져오기
    fun getPost() {
       val uid= FBAuth.getUid()

    }
}
