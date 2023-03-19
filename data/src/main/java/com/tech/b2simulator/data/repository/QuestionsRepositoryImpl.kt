package com.tech.b2simulator.data.repository

import com.tech.b2simulator.data.local.QuestionsDao
import com.tech.b2simulator.data.mapper.toQuestionEntity
import com.tech.b2simulator.data.mapper.toQuestionInfo
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(private val dao: QuestionsDao) :
    QuestionRepository {

    override fun getQuestionCountByCategoryLocation(id: Int): Int {
        Timber.d("getQuestionCountByCategoryLocation with id $id")
        return dao.getQuestionsCountByCategoryLocation(id)
    }

    override fun getQuestionCountByCategoryAction(id: Int): Int {
        Timber.d("getQuestionCountByCategoryAction with id $id")
        return dao.getQuestionsCountByCategoryAction(id)
    }

    override fun getQuestionsByCategoryLocationId(categoryId: Int): Flow<List<QuestionInfo>> {
        Timber.d("getQuestionsByCategoryLocationId")
        return dao.getQuestionsByCategoryLocation(categoryId).map {
            it.map { item ->
                item.toQuestionInfo()
            }
        }
    }

    override fun getQuestionsByCategoryLocationIdWithLimit(
        categoryId: Int,
        limit: Int
    ): Flow<List<QuestionInfo>> {
        Timber.d("getQuestionsByCategoryLocationIdWithLimit")
        return dao.getQuestionsByCategoryLocationIdWithLimit(categoryId, limit).map {
            it.map { item ->
                item.toQuestionInfo()
            }
        }
    }

    override fun getQuestionsByCategoryActionId(categoryId: Int): Flow<List<QuestionInfo>> {
        Timber.d("getQuestionsByCategoryActionId")
        return dao.getQuestionsByCategoryAction(categoryId).map {
            it.map { item ->
                item.toQuestionInfo()
            }
        }
    }

    override fun getQuestionByWrongAnswer(): Flow<List<QuestionInfo>> {
        Timber.d("getQuestionByWrongAnswer")
        return dao.getQuestionByWrongAnswer().map {
            it.map { item ->
                item.toQuestionInfo()
            }
        }
    }

    override fun getSavedQuestions(): Flow<List<QuestionInfo>> {
        Timber.d("getSavedQuestions")
        return dao.getSavedQuestions().map {
            it.map { item ->
                item.toQuestionInfo()
            }
        }
    }

    override suspend fun saveQuestionScore(question: QuestionInfo) {
        Timber.d("saveQuestionScore")
        val data = question.toQuestionEntity()
        dao.updateQuestion(data)
    }

    override fun getCategoryLocationProgress(categoryId: Int): Flow<Int> {
        Timber.d("getCategoryLocationProgress")
        return dao.getCategoryLocationProgress(categoryId)
    }

    override fun getCategoryActionProgress(categoryId: Int): Flow<Int> {
        Timber.d("getCategoryActionProgress")
        return dao.getCategoryActionProgress(categoryId)
    }
}