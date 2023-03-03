package com.tech.b2simulator.domain.usecase


import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.b2simulator.domain.repository.CategoryRepository
import com.tech.b2simulator.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetLocationCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val questionRepository: QuestionRepository
) {
    fun invoke(context: CoroutineContext): Flow<List<CategoryLocationInfo>> {
        Timber.d("GetLocationCategoryUseCase invoke")
        return categoryRepository.getLocationCategory()
            .map {
                Timber.d("GetLocationCategoryUseCase data mapping")
                it.map { item ->
                    item.total = questionRepository.getQuestionCountByCategoryLocation(item.id)
                    item.progress = questionRepository.getCategoryLocationProgress(item.id).first()
                    item
                }
            }
            .flowOn(context)
    }
}