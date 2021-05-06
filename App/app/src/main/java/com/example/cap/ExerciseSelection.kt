package com.example.cap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

public class ExerciseSelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_selection)

        val latpull : ImageButton = findViewById(R.id.Latpull)
        latpull.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            nextIntent.putExtra("exercise", "랫풀다운")
            startActivity(nextIntent)
        }
        val bench : ImageButton = findViewById(R.id.Bench)
        bench.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)

            nextIntent.putExtra("exercise", "벤치프레스")

            startActivity(nextIntent)
        }
        val squat : ImageButton = findViewById(R.id.Squat)
        squat.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)

            startActivity(nextIntent)
        }
        val deadlift : ImageButton = findViewById(R.id.Deadlift)
        deadlift.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            startActivity(nextIntent)
        }
    }

    private fun setFrag(fragNum : Int){
        val ft  = supportFragmentManager.beginTransaction()
        when(fragNum){
            0 -> {
                ft.replace(R.id.main_frame, Mypage()).commit()
            }
        }
    }
}