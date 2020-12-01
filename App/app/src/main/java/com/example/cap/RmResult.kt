package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RmResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rm_result)
        val W0 = intent.extras!!.getInt("rmweight")
        val rm = W0*0.025*7+W0
        val retext : TextView = findViewById(R.id.rmtext)
        retext.text = "${rm}"

        val Ok : Button = findViewById(R.id.OK)
        Ok.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            startActivity(nextIntent)
        }
    }
}