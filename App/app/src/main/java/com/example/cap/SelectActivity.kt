package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)
        val latpull : Button = findViewById(R.id.Latpull)
        latpull.setOnClickListener {
            val nextIntent = Intent(this, ExampleActivity::class.java)
            startActivity(nextIntent)
        }
        val bench : Button = findViewById(R.id.Bench)
        bench.setOnClickListener {
            val nextIntent = Intent(this, ExampleActivity::class.java)
            startActivity(nextIntent)
        }
    }
}