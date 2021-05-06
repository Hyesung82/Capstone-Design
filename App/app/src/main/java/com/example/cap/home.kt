package com.example.cap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.exercise_selection.*


class home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState:Bundle?

    ):View? {
        val view = inflater.inflate(com.example.cap.R.layout.exercise_selection, container, false)
        return view}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latpull: ImageButton = view.findViewById(com.example.cap.R.id.Latpull)

        latpull.setOnClickListener {

            val nextIntent = Intent(activity, ActivitySelection::class.java)
            nextIntent.putExtra("exercise", "랫풀다운")
            startActivity(nextIntent)
        }
        val bench: ImageButton = view.findViewById(com.example.cap.R.id.Bench)
            bench.setOnClickListener {

            val nextIntent = Intent(activity, ActivitySelection::class.java)
            nextIntent.putExtra("exercise", "벤치프레스")
            startActivity(nextIntent)
        }
        val squat : ImageButton = view.findViewById(R.id.Squat)
        squat.setOnClickListener {
            val nextIntent = Intent(activity, ActivitySelection::class.java)
            nextIntent.putExtra("exercise", "스쿼트")
            startActivity(nextIntent)
        }
        val deadlift : ImageButton = view.findViewById(R.id.Deadlift)
        deadlift.setOnClickListener {
            val nextIntent = Intent(activity, ActivitySelection::class.java)
            nextIntent.putExtra("exercise", "데드리프트")
            startActivity(nextIntent)
        }

    }}







