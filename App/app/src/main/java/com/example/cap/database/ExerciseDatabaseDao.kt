package com.example.cap.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface ExerciseDatabaseDao {
    @Insert
    suspend fun insert(exercise: ExerciseInfo)

    @Update
    suspend fun update(exercise: ExerciseInfo)

    @Query("SELECT * from daily_exercise_info_table WHERE exerciseId = :key")
    suspend fun get(key: Long): ExerciseInfo

    @Query("SELECT * from daily_exercise_info_table WHERE date = :date")
    suspend fun getToday(date: Date): ExerciseInfo
}