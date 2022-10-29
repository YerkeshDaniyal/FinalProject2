package com.example.finalproject2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.finalproject2.R
import com.example.finalproject2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

private lateinit var binding: ActivityMainBinding
private lateinit var homeFragment: HomeFragment
private lateinit var searchFragment: SearchFragment

private var currentFragment: Fragment? = null

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()

        binding.mainNavBottom.setOnNavigationItemSelectedListener(this)
        binding.mainNavBottom.selectedItemId = R.id.menu_home

    }




}