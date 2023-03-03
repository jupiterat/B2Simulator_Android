package com.tech.b2simulator.domain.usecase

import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.repository.QuestionRepository
import javax.inject.Inject

class SaveQuestionScoreUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {
    suspend fun invoke(question: QuestionInfo) {
        questionRepository.saveQuestionScore(question)
    }
}