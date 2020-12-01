package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ExerciseResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_result)
        val button : Button = findViewById(R.id.button)
        val weight : TextView = findViewById(R.id.resultweight)
        val weight2 = intent.extras!!.getInt("resultweight")
        //if(intent.hasExtra("resultweight")){
           // weight.text = intent.getStringExtra("resultweight")
        //}
        weight.text = "${weight2}"
        button.setOnClickListener {
            val nextIntent = Intent(this, ExerciseSelection::class.java)
            startActivity(nextIntent)
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()

    }
}
