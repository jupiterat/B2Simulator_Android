package com.tech.b2simulator.data.repository

import com.tech.b2simulator.data.local.ExamDao
import com.tech.b2simulator.data.mapper.toExam
import com.tech.b2simulator.data.mapper.toExamInfo
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examDao: ExamDao
) : ExamRepository {

    override fun getExams(): Flow<List<ExamInfo>> {
        return examDao.getExams().map {
            it.map { item ->
                item.toExamInfo()
            }
        }
    }

    override suspend fun updateExam(exam: ExamInfo) {
        val data = exam.toExam()
        examDao.updateExam(data)
    }

    override suspend fun clearScore() {
        examDao.resetScore()
    }
}