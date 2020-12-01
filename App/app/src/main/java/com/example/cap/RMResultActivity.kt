package com.example.cap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView

class RMResultActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise)

        val tvIterNum: TextView = findViewById(R.id.tvIterNum)
        tvIterNum.setText("충분히 무겁다고 생각하는 무게를 7번 반복해주세요!")


        handler= Handler()
        handler.postDelayed({
            doSomething()
        },2000)
    }

    private fun doSomething() {
        startActivity(Intent(this, RmResult::class.java))
        //Toast.makeText(this,"Hi! I am Toast Message",Toast.LENGTH_SHORT).show()
    }
}



