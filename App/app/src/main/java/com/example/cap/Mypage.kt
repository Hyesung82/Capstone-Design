package com.example.cap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment



class Mypage : Fragment() {
   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState:Bundle?
   ):View? {
        val view = inflater.inflate(R.layout.mypage, container, false)
   return view
   }
}