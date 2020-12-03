package com.example.cap

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ActivitySelection : AppCompatActivity() {
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        videoView = findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.lat_pulldown}")
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(videoUri)

        videoView.requestFocus()
        videoView.start()

        videoView.setOnCompletionListener {
            Toast.makeText(applicationContext, "Video completed", Toast.LENGTH_LONG).show()
        }
        videoView.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An error occured while playing video",
                    Toast.LENGTH_LONG).show()
            false
        }


        val select : TextView = findViewById(R.id.tvExercise)
        if(intent.hasExtra("exercise")){
            select.text = " ${intent.getStringExtra("exercise")} "
        }

        val Set : Button = findViewById(R.id.Set)
        Set.setOnClickListener {
            val nextIntent = Intent(this, Exercise::class.java)
            nextIntent.putExtra("activity", "init")
            startActivity(nextIntent)
        }
        val RM : Button = findViewById(R.id.RM)
        RM.setOnClickListener {
            val nextIntent = Intent(this, WeightInput::class.java)
            nextIntent.putExtra("activity", "rm")
            startActivity(nextIntent)
        }
        val Exercise : Button = findViewById(R.id.Exercise)
        Exercise.setOnClickListener {
//            val nextIntent = Intent(this, ExerciseWeightInput::class.java)
            val nextIntent = Intent(this, WeightInput::class.java)
            nextIntent.putExtra("activity", "exercise")
            startActivity(nextIntent)
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoView != null && videoView.isPlaying) videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (videoView != null) videoView.stopPlayback()
    }
}