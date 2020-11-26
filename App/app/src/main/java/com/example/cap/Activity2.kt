package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        val Set : Button = findViewById(R.id.Set)
        Set.setOnClickListener {
            val nextIntent = Intent(this, Activity3::class.java)
            startActivity(nextIntent)
        }
        val RM : Button = findViewById(R.id.RM)
        RM.setOnClickListener {
            val nextIntent = Intent(this, Activity5::class.java)
            startActivity(nextIntent)
        }
        val Exercise : Button = findViewById(R.id.Exercise)
        Exercise.setOnClickListener {
            val nextIntent = Intent(this, Activity8::class.java)
            startActivity(nextIntent)
        }
    }
}