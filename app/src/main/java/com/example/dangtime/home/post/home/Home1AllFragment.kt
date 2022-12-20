package com.example.dangtime.home.post.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.adapter.PostAdapter
import com.example.dangtime.model.PostVO

class Home1AllFragment : Fragment() {

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

        // item
        val postList = ArrayList<PostVO>()

        // adapter
        val adapter = PostAdapter(
            requireContext(),
            postList
        )

        // adapter container 연결
        rvPostHomeAll.adapter = adapter
        rvPostHomeAll.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
}