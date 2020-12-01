package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ExerciseResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_result)
        val Button : Button = findViewById(R.id.button)
        Button.setOnClickListener {
            val nextIntent = Intent(this, ExerciseSelection::class.java)
            startActivity(nextIntent)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}
