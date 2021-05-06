package com.example.cap

import android.content.Intent


import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView


class fragment : AppCompatActivity() {


   private val fragmentManager: FragmentManager = supportFragmentManager
   private val fragmentMypage: Mypage = Mypage()
    private val fragmenthome = home()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.cap.R.layout.framelayout)

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.cap.R.id.main_frame, Infomation()).commitAllowingStateLoss()
        val bottomNavigationView = findViewById<BottomNavigationView>(com.example.cap.R.id.navigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(ItemSelectedListener())
    }

    internal inner class ItemSelectedListener :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            when (menuItem.getItemId()) {
               com.example.cap.R.id.infoItem -> transaction.replace(com.example.cap.R.id.main_frame, Infomation())
                    .commitAllowingStateLoss()
                com.example.cap.R.id.homeItem -> transaction.replace(com.example.cap.R.id.main_frame, home())
                    .commitAllowingStateLoss()
                com.example.cap.R.id.mypageItem -> transaction.replace(com.example.cap.R.id.main_frame, Mypage())
                    .commitAllowingStateLoss()
            }
            return true
        }
    }


    }




