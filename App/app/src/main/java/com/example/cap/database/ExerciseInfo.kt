package com.example.cap.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily_exercise_info_table")
data class ExerciseInfo (
    @PrimaryKey(autoGenerate = true)
    var exerciseId: Long = 0L,

    @ColumnInfo(name = "date")
    val date: Date = Date(System.currentTimeMillis()),

    @ColumnInfo(name = "exercise_name")
    val exerciseName: String = "랫풀다운",

    @ColumnInfo(name = "achievement")
    val achievement: Int = 0
)