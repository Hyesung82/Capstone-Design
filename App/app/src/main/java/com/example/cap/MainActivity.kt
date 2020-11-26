package com.example.cap
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mButton.setOnClickListener {
            val nextIntent = Intent(this, SecondActivity::class.java)
            startActivity(nextIntent)
        }
    }
}