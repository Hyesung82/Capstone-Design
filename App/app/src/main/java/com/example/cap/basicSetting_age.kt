package com.example.cap

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

class basicSetting_age :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_setting_age)

        val button : Button = findViewById(R.id.age_btn)

        button.setOnClickListener{
            val nextIntent = Intent(this, fragment::class.java)
            startActivity(nextIntent)
        }

    }

}