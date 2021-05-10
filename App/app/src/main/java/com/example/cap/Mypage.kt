package com.example.cap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cap.adapter.ExerciseListAdapter
import com.example.cap.database.ExerciseInfo
import com.example.cap.database.ExerciseInfoViewModel
import com.example.cap.database.ExerciseViewModelFactory
import com.example.cap.database.ExercisesApplication


class Mypage : Fragment() {
    private val exerciseActivityRequestCode = 1

    private val exerciseViewModel: ExerciseInfoViewModel by viewModels {
        ExerciseViewModelFactory((activity?.application as ExercisesApplication).repository)
    }

    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState:Bundle?
    ):View? {
       val view = inflater.inflate(R.layout.mypage, container, false)

       val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
       val adapter = ExerciseListAdapter()
       recyclerView.adapter = adapter
       recyclerView.layoutManager = LinearLayoutManager(activity)

       exerciseViewModel.allExercise.observe(viewLifecycleOwner, Observer { exercises ->
           exercises?.let { adapter.submitList(it) }
       })

       return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO: 호출되지 않음
        Log.i("Mypage", "onActivityResult 호출")

        if (requestCode == exerciseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getLongExtra(Exercise.EXTRA_REPLY, System.currentTimeMillis())?.let {
                Log.i("Mypage", "운동 시간: $it")

                val exercise = ExerciseInfo(it)
                exerciseViewModel.insert(exercise)
            }
        } else {
            Log.e("Mypage", "응답 실패")
        }
    }
}