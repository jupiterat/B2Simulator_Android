package com.tech.b2simulator.domain.usecase

import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetExamUseCase @Inject constructor(private val repository: ExamRepository) {
    fun invoke(context: CoroutineContext): Flow<List<ExamInfo>> {
        return repository.getExams().flowOn(context)
    }
}