package com.tech.b2simulator.data.repository

import com.tech.b2simulator.data.local.CategoryDao
import com.tech.b2simulator.data.local.ExamDao
import com.tech.b2simulator.data.local.QuestionsDao
import com.tech.b2simulator.data.mapper.toExam
import com.tech.b2simulator.data.mapper.toExamInfo
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examDao: ExamDao,
    private val questionDao: QuestionsDao,
    private val categoryLocationDao: CategoryDao
) : ExamRepository {
    private val defaultExamQuestionIDs = arrayOf(
        intArrayOf(1, 2, 30, 44, 45, 64, 74, 75, 91, 92),
        intArrayOf(3, 4, 31, 46, 47, 65, 76, 77, 93, 94),
        intArrayOf(5, 6, 32, 48, 49, 66, 78, 79, 95, 96),
        intArrayOf(7, 8, 33, 50, 51, 67, 80, 81, 97, 98),
        intArrayOf(9, 10, 34, 52, 53, 68, 82, 83, 99, 100),
        intArrayOf(11, 12, 35, 54, 55, 69, 84, 85, 101, 102),
        intArrayOf(13, 14, 36, 56, 57, 70, 86, 87, 103, 104),
        intArrayOf(15, 16, 37, 58, 59, 71, 88, 89, 105, 106),
        intArrayOf(17, 18, 38, 60, 61, 72, 89, 90, 107, 108),
        intArrayOf(19, 20, 39, 62, 63, 73, 87, 88, 109, 110),
        intArrayOf(21, 22, 40, 61, 62, 72, 85, 86, 111, 112),
        intArrayOf(23, 24, 41, 59, 60, 71, 83, 84, 113, 114),
        intArrayOf(25, 26, 42, 57, 58, 70, 81, 82, 115, 116),
        intArrayOf(27, 28, 43, 55, 56, 69, 79, 80, 117, 118),
        intArrayOf(28, 29, 42, 53, 54, 68, 77, 78, 119, 120)
    )

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
}