package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RmResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rm_result)
        val Ok : Button = findViewById(R.id.OK)
        Ok.setOnClickListener {
            val nextIntent = Intent(this, ActivitySelection::class.java)
            startActivity(nextIntent)
        }
    }
}