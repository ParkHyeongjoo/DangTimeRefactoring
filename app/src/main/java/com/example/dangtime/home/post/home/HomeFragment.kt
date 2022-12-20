package com.example.dangtime.home.post.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dangtime.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val bnvPost = view.findViewById<BottomNavigationView>(R.id.bnvPost)

        childFragmentManager.beginTransaction().replace(
            R.id.flPostHome,
            Home1AllFragment()
        ).commit()

        bnvPost.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tabPost1 -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.flPostHome,
                        Home1AllFragment()
                    ).commit()
                }
                R.id.tabPost2 -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.flPostHome,
                        Home2MateFragment()
                    ).commit()
                }
                R.id.tabPost3 -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.flPostHome,
                        Home3TalkFragment()
                    ).commit()
                }
            }
            true
        }


        return view
    }

}