package com.tech.b2simulator.domain.usecase

import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetQuestionByCategoryActionIdUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {
    fun invoke(context: CoroutineContext, categoryId: Int): Flow<List<QuestionInfo>> {
        return questionRepository.getQuestionsByCategoryActionId(categoryId)
            .flowOn(context)
    }
}