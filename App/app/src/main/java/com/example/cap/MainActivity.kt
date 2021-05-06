package com.example.cap


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_start: Button = findViewById(R.id.btn_start)
        btn_start.setOnClickListener {
            val nextIntent = Intent(this, fragment::class.java)
            startActivity(nextIntent)
        }
        val btn_start2: Button = findViewById(R.id.btn_start2)
        btn_start2.setOnClickListener {
            val nextIntent = Intent(this, basicSetting_sex::class.java)
            startActivity(nextIntent)
    }
}
}
