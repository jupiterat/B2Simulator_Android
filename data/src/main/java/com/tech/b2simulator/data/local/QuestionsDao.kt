package com.tech.b2simulator.data.local

import androidx.room.Dao
import androidx.room.Query
import com.tech.b2simulator.data.local.entity.Questions

@Dao
interface QuestionsDao {
    @Query("SELECT * FROM Questions")
    suspend fun getQuestions(): List<Questions>

    @Query("SELECT * FROM Questions WHERE groupByLocation = :id")
    suspend fun getQuestionsByCategoryLocation(id: Int): List<Questions>

    @Query("SELECT COUNT(*) FROM Questions WHERE groupByLocation = :id")
    suspend fun getQuestionsCountByCategoryLocation(id: Int): Int

    @Query("SELECT * FROM Questions WHERE groupByAction = :id")
    suspend fun getQuestionsByCategoryAction(id: Int): List<Questions>

    @Query("SELECT COUNT(*) FROM Questions WHERE groupByAction = :id")
    suspend fun getQuestionsCountByCategoryAction(id: Int): Int
}