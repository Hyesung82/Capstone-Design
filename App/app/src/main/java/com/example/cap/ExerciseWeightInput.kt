package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlin.properties.Delegates

class ExerciseWeightInput : AppCompatActivity() {
    var intWeight by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_weight_input)
        val etWeight : EditText = findViewById(R.id.etWeight)
        val Ok : Button = findViewById(R.id.OK)
        Ok.setOnClickListener {
            val strWeight: String = etWeight.text.toString()
            intWeight = strWeight.toInt()
            doSomething()
        }
    }
    private fun doSomething() {
        val nextIntent = Intent(this, Exercise::class.java)
        nextIntent.putExtra("activity", "exercise")
        nextIntent.putExtra("weight", intWeight)
        startActivity(nextIntent)
        //Toast.makeText(this,"Hi! I am Toast Message",Toast.LENGTH_SHORT).show()
    }
}