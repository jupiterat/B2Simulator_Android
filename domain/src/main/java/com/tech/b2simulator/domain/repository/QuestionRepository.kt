package com.tech.b2simulator.domain.repository

interface QuestionRepository {
    suspend fun getQuestionCountByCategoryLocation(id: Int): Int
    suspend fun getQuestionCountByCategoryAction(id: Int): Int
}