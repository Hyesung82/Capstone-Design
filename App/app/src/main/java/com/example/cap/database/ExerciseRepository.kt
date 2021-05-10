package com.example.cap.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDatabaseDao) {

    val allExercise: Flow<List<ExerciseInfo>> = exerciseDao.get()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(exercise: ExerciseInfo) {
        exerciseDao.insert(exercise)
    }
}