package com.example.cap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment


class home : Fragment() {
    private val TAG = "home"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState:Bundle?): View? {
        val view = inflater.inflate(R.layout.exercise_selection, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val latpull : ImageButton = view.findViewById(R.id.Latpull)
        latpull.setOnClickListener {
            Log.i(TAG, "latpull.setOnClickListener 호출")

            editor.putString(getString(R.string.saved_exercise), "랫풀다운")
            editor.commit()

            Log.i(TAG, "현재 운동: ${sharedPref.getString(getString(R.string.saved_exercise), "값 없음")}")

            val nextIntent = Intent(activity, ActivitySelection::class.java)
            startActivity(nextIntent)
        }

        val bench : ImageButton = view.findViewById(R.id.Bench)
        bench.setOnClickListener {
            editor.putString(getString(R.string.saved_exercise), "벤치프레스")
            editor.commit()

            Log.i(TAG, "현재 운동: ${sharedPref.getString(getString(R.string.saved_exercise), "값 없음")}")

            val nextIntent = Intent(activity, ActivitySelection::class.java)
            startActivity(nextIntent)
        }

        val squat : ImageButton = view.findViewById(R.id.Squat)
        squat.setOnClickListener {
            editor.putString(getString(R.string.saved_exercise), "스쿼트")
            editor.commit()

            Log.i(TAG, "현재 운동: ${sharedPref.getString(getString(R.string.saved_exercise), "값 없음")}")

            val nextIntent = Intent(activity, ActivitySelection::class.java)
            startActivity(nextIntent)
        }

        val deadlift : ImageButton = view.findViewById(R.id.Deadlift)
        deadlift.setOnClickListener {
            editor.putString(getString(R.string.saved_exercise), "데드리프트")
            editor.commit()

            Log.i(TAG, "현재 운동: ${sharedPref.getString(getString(R.string.saved_exercise), "값 없음")}")

            val nextIntent = Intent(activity, ActivitySelection::class.java)
            startActivity(nextIntent)
        }
    }
}







