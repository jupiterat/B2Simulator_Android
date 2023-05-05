package com.tech.b2simulator.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.tech.b2simulator.data.local.entity.Exams
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Query("SELECT * FROM Exams")
    fun getExams(): Flow<List<Exams>>

    @Update(entity = Exams::class)
    suspend fun updateExam(exams: Exams)

    @Query("UPDATE exams SET score = -1 WHERE score != -1")
    fun resetScore()
}