package com.example.cap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cap.adapter.ExerciseListAdapter
import com.example.cap.database.ExerciseInfoViewModel
import com.example.cap.database.ExerciseViewModelFactory
import com.example.cap.database.ExercisesApplication


class Mypage : Fragment() {
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
}