package com.tech.b2simulator.domain.usecase


import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.b2simulator.domain.repository.CategoryRepository
import com.tech.b2simulator.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetLocationCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val questionRepository: QuestionRepository
) {
    fun invoke(context: CoroutineContext): Flow<List<CategoryLocationInfo>> {
        return categoryRepository.getLocationCategory()
            .map {
                it.map { item ->
                    item.total = questionRepository.getQuestionCountByCategoryAction(item.id)
                    item
                }
            }.flowOn(context)
    }
}