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
        val tvTimes : TextView = findViewById(R.id.tvTimes)
        val tvSet : TextView = findViewById(R.id.tvSet)

        val weight2 = intent.extras!!.getInt("resultweight")
        val numTimes = intent.extras!!.getInt("resultTimes")
        val numSet = intent.extras!!.getInt("resultSet")
        //if(intent.hasExtra("resultweight")){
           // weight.text = intent.getStringExtra("resultweight")
        //}
        weight.text = "${weight2}"
        tvTimes.text = "${numTimes}"
        tvSet.text = "${numSet}"

        button.setOnClickListener {
            val nextIntent = Intent(this, ExerciseSelection::class.java)
            startActivity(nextIntent)
        }
    }


    override fun onBackPressed() {
//        super.onBackPressed()
    }
}
