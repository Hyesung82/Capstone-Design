package com.example.cap

import android.content.Context
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

        val sharedPref = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        videoView = findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.lat_pulldown}")
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(videoUri)

        videoView.requestFocus()
        videoView.start()

        videoView.setOnCompletionListener {
//            Toast.makeText(applicationContext, "Video completed", Toast.LENGTH_LONG).show()
        }
        videoView.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An error occured while playing video",
                    Toast.LENGTH_LONG).show()
            false
        }


        val select : TextView = findViewById(R.id.tvExercise)
        val ivSensor: ImageView = findViewById(R.id.ivSensor)

        val curExercise = sharedPref.getString(getString(R.string.saved_exercise), "값 없음")
        select.text = " $curExercise "
        when(curExercise) {
            "랫풀다운" -> ivSensor.setImageResource(R.drawable.sensor_lat_pulldown)
            "벤치프레스" -> ivSensor.setImageResource(R.drawable.sensor_lat_pulldown)
            "스쿼트" -> ivSensor.setImageResource(R.drawable.sensor_lat_pulldown)
            "데드리프트" -> ivSensor.setImageResource(R.drawable.sensor_lat_pulldown)
            else -> ivSensor.setImageResource(R.drawable.sensor_lat_pulldown)
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
            val nextIntent = Intent(this, GoalSetting::class.java)
            nextIntent.putExtra("activity", "exercise")
            startActivity(nextIntent)
        }

        val intent = intent
        val activity = intent.extras?.getString("activity")
        val initialSetting = when (curExercise) {
            "랫풀다운" -> sharedPref.getBoolean(getString(R.string.saved_initial_lat_pull_down), false)
            "벤치프레스" -> sharedPref.getBoolean(getString(R.string.saved_initial_bench_press), false)
            "스쿼트" -> sharedPref.getBoolean(getString(R.string.saved_initial_squat), false)
            else -> sharedPref.getBoolean(getString(R.string.saved_initial_dead_lift), false)
        }
        val rmSetting = when (curExercise) {
            "랫풀다운" -> sharedPref.getFloat(getString(R.string.saved_rm_lat_pull_down), 0F)
            "벤치프레스" -> sharedPref.getFloat(getString(R.string.saved_rm_bench_press), 0F)
            "스쿼트" -> sharedPref.getFloat(getString(R.string.saved_rm_squat), 0F)
            else -> sharedPref.getFloat(getString(R.string.saved_rm_dead_lift), 0F)
        }

        Exercise.isEnabled = initialSetting && (rmSetting > 0)
    }

    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}