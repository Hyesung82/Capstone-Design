package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ExerciseSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_selection)
        val latpull : Button = findViewById(R.id.Latpull)
        latpull.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            nextIntent.putExtra("msg",latpull.text.toString())
            startActivity(nextIntent)
        }
        val bench : Button = findViewById(R.id.Bench)
        bench.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            nextIntent.putExtra("msg",bench.text.toString())
            startActivity(nextIntent)
        }
        val squat : Button = findViewById(R.id.Squat)
        squat.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            nextIntent.putExtra("msg",squat.text.toString())
            startActivity(nextIntent)
        }
        val deadlift : Button = findViewById(R.id.Deadlift)
        deadlift.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            nextIntent.putExtra("msg",deadlift.text.toString())
            startActivity(nextIntent)
        }
    }
}