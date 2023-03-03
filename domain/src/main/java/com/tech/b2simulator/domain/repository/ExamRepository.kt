package com.tech.b2simulator.domain.repository

import com.tech.b2simulator.domain.model.ExamInfo
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    fun getExams(): Flow<List<ExamInfo>>
    suspend fun updateExam(exam: ExamInfo)
}