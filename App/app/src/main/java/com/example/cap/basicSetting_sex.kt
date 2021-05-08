package com.example.cap

import android.content.Intent
import android.os.Bundle

import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity




class basicSetting_sex :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_setting_sex)

        val female : ImageButton = findViewById(R.id.female)
        val male : ImageButton = findViewById(R.id.male)
        female.setOnClickListener{
            val nextIntent = Intent(this, basicSetting_age::class.java)
            startActivity(nextIntent)
        }
        male.setOnClickListener{
            val nextIntent = Intent(this, basicSetting_age::class.java)
            startActivity(nextIntent)
        }


    }

}


