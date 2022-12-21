package com.example.dangtime.home.post.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dangtime.R
import com.example.dangtime.home.post.home.Home1AllFragment
import com.example.dangtime.home.post.home.Home2MateFragment
import com.example.dangtime.home.post.home.Home3TalkFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookmarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)

        val bnvPostBookmark = view.findViewById<BottomNavigationView>(R.id.bnvPostBookmark)

        childFragmentManager.beginTransaction().replace(
            R.id.flPostBookmark,
            Bookmark1AllFragment()
        ).commit()

        bnvPostBookmark.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tabPost1 -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.flPostBookmark,
                        Bookmark1AllFragment()
                    ).commit()
                }
                R.id.tabPost2 -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.flPostBookmark,
                        Bookmark2MateFragment()
                    ).commit()
                }
                R.id.tabPost3 -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.flPostBookmark,
                        BookMark3TalkFragment()
                    ).commit()
                }
            }
            true
        }

        return view
    }

}