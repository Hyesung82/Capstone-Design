package com.example.cap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_start: Button = findViewById(R.id.btn_start)
        btn_start.setOnClickListener {
            val nextIntent = Intent(this, SelectActivity::class.java)
            startActivity(nextIntent)
        }
    }
}