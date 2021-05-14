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
        val view = inflater.inflate(com.example.cap.R.layout.athletic_field_selection, container, false)
        return view}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fitness: ImageButton = view.findViewById(R.id.fitness)
        fitness.setOnClickListener{

            val nextIntent = Intent(activity, ExerciseSelection::class.java)
            startActivity(nextIntent)
        }

        val yoga: ImageButton = view.findViewById(R.id.yoga)
        yoga.setOnClickListener{
            val nextIntent = Intent(activity, YogaSelection::class.java)
            startActivity(nextIntent)
        }


    }}







