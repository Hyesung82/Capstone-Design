package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ExerciseWeightInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_weight_input)
        val Ok : Button = findViewById(R.id.OK)
        Ok.setOnClickListener {
            val nextIntent = Intent(this, Exercise::class.java)
            nextIntent.putExtra("activity", "exercise")
            startActivity(nextIntent)
        }
    }
}