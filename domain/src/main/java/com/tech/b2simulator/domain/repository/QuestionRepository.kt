package com.tech.b2simulator.domain.repository

import com.tech.b2simulator.domain.model.QuestionInfo
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getQuestionCountByCategoryLocation(id: Int): Int
    fun getQuestionCountByCategoryAction(id: Int): Int
    fun getQuestionsByCategoryLocationId(categoryId: Int): Flow<List<QuestionInfo>>
    fun getQuestionsByCategoryLocationIdWithLimit(
        categoryId: Int,
        limit: Int
    ): Flow<List<QuestionInfo>>

    fun getQuestionsByCategoryActionId(categoryId: Int): Flow<List<QuestionInfo>>
    fun getQuestionByWrongAnswer(): Flow<List<QuestionInfo>>
    fun getCategoryLocationProgress(categoryId: Int): Flow<Int>
    fun getCategoryActionProgress(categoryId: Int): Flow<Int>
    fun getSavedQuestions(): Flow<List<QuestionInfo>>
    suspend fun saveQuestionScore(question: QuestionInfo)
    suspend fun clearSavedQuestions()
    suspend fun clearScore()
}