package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        val Set : Button = findViewById(R.id.Set)
        Set.setOnClickListener {
            val nextIntent = Intent(this, SetupActivity::class.java)
            startActivity(nextIntent)
        }
        val RM : Button = findViewById(R.id.RM)
        RM.setOnClickListener {
            val nextIntent = Intent(this, SetupActivity::class.java)
            nextIntent.putExtra("activity", "rm")
            startActivity(nextIntent)
        }
        val Exercise : Button = findViewById(R.id.Exercise)
        Exercise.setOnClickListener {
            val nextIntent = Intent(this, InputWeightActivity::class.java)
            startActivity(nextIntent)
        }
    }
}