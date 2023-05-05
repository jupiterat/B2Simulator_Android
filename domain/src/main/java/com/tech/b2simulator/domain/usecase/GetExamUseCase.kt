package com.tech.b2simulator.domain.usecase

import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.repository.CategoryRepository
import com.tech.b2simulator.domain.repository.ExamRepository
import com.tech.b2simulator.domain.repository.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetExamUseCase @Inject constructor(
    private val repository: ExamRepository,
    private val questionRepository: QuestionRepository,
    private val categoryRepository: CategoryRepository
) {
    fun invoke(context: CoroutineContext): Flow<List<ExamInfo>> {
        return repository.getExams().distinctUntilChanged().map { exams ->
            exams.map {
                it.questions = generateQuestions(context).first()
                it
            }
        }.flowOn(context)
    }

    private suspend fun generateQuestions(context: CoroutineContext): Flow<List<QuestionInfo>> {
        return categoryRepository.getLocationCategory()
            .map { categories ->
                categories.map {
                    CoroutineScope(context).async {
                        questionRepository.getQuestionsByCategoryLocationIdWithLimit(
                            it.id,
                            it.totalInExam
                        ).first()
                    }
                }.awaitAll()
            }.map { list ->
                list.flatten()
            }.map {
                it.sortedBy { question ->
                    question.id
                }
            }
    }

}