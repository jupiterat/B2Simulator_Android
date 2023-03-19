package com.tech.b2simulator.domain.usecase

import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.repository.ExamRepository
import javax.inject.Inject

class SaveExamScoreUseCase @Inject constructor(
    private val examRepository: ExamRepository
) {
    suspend fun invoke(exam: ExamInfo) {
        examRepository.updateExam(exam)
    }
}