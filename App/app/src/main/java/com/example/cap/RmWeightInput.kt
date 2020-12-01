package com.example.cap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class RmWeightInput : AppCompatActivity() {
    var intWeight by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rm_weight_input)

        val etRmWeight: EditText = findViewById(R.id.etRmWeight)
        val bRmWeight: Button = findViewById(R.id.bRmWeight)
        bRmWeight.setOnClickListener{
            val strWeight: String = etRmWeight.text.toString()
            intWeight = strWeight.toInt()
            doSomething()
        }
    }

    private fun doSomething() {
        val nextIntent = Intent(this, Exercise::class.java)
        nextIntent.putExtra("activity", "rm")
        nextIntent.putExtra("weight", intWeight)
        startActivity(nextIntent)
        //Toast.makeText(this,"Hi! I am Toast Message",Toast.LENGTH_SHORT).show()
    }
}



