package com.tech.b2simulator.domain.usecase

import com.tech.b2simulator.domain.repository.ExamRepository
import com.tech.b2simulator.domain.repository.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ClearDataUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository
) {
    fun invoke(context: CoroutineContext) {
        CoroutineScope(context).launch {
            listOf(
                launch { questionRepository.clearScore() },
                launch { questionRepository.clearSavedQuestions() },
                launch { examRepository.clearScore() }
            ).joinAll()
        }
    }
}