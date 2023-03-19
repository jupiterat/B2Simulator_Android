package com.tech.b2simulator.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.tech.b2simulator.data.local.entity.Questions
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {
    @Query("SELECT * FROM Questions")
    fun getQuestions(): Flow<List<Questions>>

    @Query("SELECT * FROM Questions WHERE groupByLocation = :id")
    fun getQuestionsByCategoryLocation(id: Int): Flow<List<Questions>>

    @Query("SELECT * FROM Questions WHERE groupByLocation = :id order by random() limit :limit")
    fun getQuestionsByCategoryLocationIdWithLimit(id: Int, limit: Int): Flow<List<Questions>>

    @Query("SELECT COUNT(*) FROM Questions WHERE groupByLocation = :id")
    fun getQuestionsCountByCategoryLocation(id: Int): Int

    @Query("SELECT * FROM Questions WHERE groupByAction = :id")
    fun getQuestionsByCategoryAction(id: Int): Flow<List<Questions>>

    @Query("SELECT COUNT(*) FROM Questions WHERE groupByAction = :id")
    fun getQuestionsCountByCategoryAction(id: Int): Int

    @Query("SELECT * FROM Questions WHERE score == 0 OR score == 1")
    fun getQuestionByWrongAnswer(): Flow<List<Questions>>

    @Query("SELECT * FROM Questions WHERE saved == 1")
    fun getSavedQuestions(): Flow<List<Questions>>

    @Update(entity = Questions::class)
    suspend fun updateQuestion(question: Questions)

    @Query("SELECT COUNT(*) FROM Questions WHERE groupByLocation = :id AND score != -1")
    fun getCategoryLocationProgress(id: Int): Flow<Int>

    @Query("SELECT COUNT(*) FROM Questions WHERE groupByAction = :id AND score != -1")
    fun getCategoryActionProgress(id: Int): Flow<Int>
}