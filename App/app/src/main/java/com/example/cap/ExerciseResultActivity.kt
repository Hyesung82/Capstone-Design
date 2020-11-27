package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ExerciseResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity7)
        val Ok : Button = findViewById(R.id.OK)
        Ok.setOnClickListener {
            val nextIntent = Intent(this, MeasureRMActivity::class.java)
            startActivity(nextIntent)
        }
    }
}