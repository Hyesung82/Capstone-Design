package com.example.cap
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class RmWeightInput : AppCompatActivity() {
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rm_weight_input)
        handler= Handler()
        handler.postDelayed({
            doSomething()
        },2000)
    }

    private fun doSomething() {
        startActivity(Intent(this, RMResultActivity::class.java))
        //Toast.makeText(this,"Hi! I am Toast Message",Toast.LENGTH_SHORT).show()
    }
}



