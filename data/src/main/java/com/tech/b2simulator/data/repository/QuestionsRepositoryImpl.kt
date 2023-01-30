package com.tech.b2simulator.data.repository

import android.util.Log
import com.tech.b2simulator.data.local.QuestionsDao
import com.tech.b2simulator.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(private val dao: QuestionsDao) :
    QuestionRepository {
    override suspend fun getQuestionCountByCategoryLocation(id: Int): Int {
        Log.e("QuestionsRepositoryImpl", "getQuestionCountByCategoryLocation with id $id")
        return dao.getQuestionsCountByCategoryLocation(id)
    }

    override suspend fun getQuestionCountByCategoryAction(id: Int): Int {
        Log.e("QuestionsRepositoryImpl", "getQuestionCountByCategoryAction with id $id")
        return dao.getQuestionsCountByCategoryAction(id)
    }
}