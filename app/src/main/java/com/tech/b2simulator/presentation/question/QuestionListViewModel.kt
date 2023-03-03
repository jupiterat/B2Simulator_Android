package com.tech.b2simulator.presentation.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.tech.b2simulator.domain.common.CategoryType
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.usecase.GetQuestionByCategoryActionIdUseCase
import com.tech.b2simulator.domain.usecase.GetQuestionByCategoryLocationIdUseCase
import com.tech.b2simulator.domain.usecase.GetQuestionByWrongAnswerUseCase
import com.tech.b2simulator.domain.usecase.GetSavedQuestionsUseCase
import com.tech.b2simulator.presentation.BaseViewModel
import com.tech.common.viewmodels.lazyMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuestionListViewModel @Inject constructor(
    private val getQuestionByCategoryLocationIdUseCase: GetQuestionByCategoryLocationIdUseCase,
    private val getQuestionByCategoryActionIdUseCase: GetQuestionByCategoryActionIdUseCase,
    private val getQuestionByWrongAnswerUseCase: GetQuestionByWrongAnswerUseCase,
    private val getSavedQuestionsUseCase: GetSavedQuestionsUseCase
) :
    BaseViewModel() {

    private val _selectedQuestion = MutableLiveData<QuestionInfo>()
    val selectedQuestion: LiveData<QuestionInfo>
        get() = _selectedQuestion

    private var selectedIndex = -1

    private var selectedCategory: CategoryType? = null

    private val questionLiveData: Map<CategoryType, LiveData<ViewState<List<QuestionInfo>>>> =
        lazyMap {
            when (it) {
                is CategoryType.LOCATION -> {
                    getQuestionByCategoryLocationId(it.id)
                }
                is CategoryType.ACTION -> {
                    getQuestionByCategoryActionId(it.id)
                }
                is CategoryType.SAVED -> {
                    getSavedQuestions()
                }
                is CategoryType.WRONG -> {
                    getWrongAnswer()
                }
            }
        }

    private fun getQuestionByCategoryLocationId(id: Int): LiveData<ViewState<List<QuestionInfo>>> {
        Timber.d("getQuestionsByCategoryLocationId $id")
        return getQuestionByCategoryLocationIdUseCase.invoke(
            Dispatchers.IO,
            id
        )
            .distinctUntilChanged()
            .catch { error ->
                Timber.e("Error happened $error")
                ViewState.Error(("Error happened"))
            }.map {
                ViewState.Success(it)
            }.asLiveData()
    }

    private fun getQuestionByCategoryActionId(id: Int): LiveData<ViewState<List<QuestionInfo>>> {
        Timber.d("getQuestionsByCategoryActionId $id")
        return getQuestionByCategoryActionIdUseCase.invoke(
            Dispatchers.IO,
            id
        )
            .distinctUntilChanged()
            .catch { error ->
                Timber.e("Error happened $error")
                ViewState.Error(("Error happened"))
            }.map {
                Timber.d("getQuestionByCategoryActionId data is collectd")
                ViewState.Success(it)
            }.asLiveData()
    }

    private fun getSavedQuestions(): LiveData<ViewState<List<QuestionInfo>>> {
        Timber.d("getSavedQuestions")
        return getSavedQuestionsUseCase.invoke(Dispatchers.IO)
            .catch { error ->
                Timber.e("Error happened $error")
                ViewState.Error(("Error happened"))
            }.map {
                Timber.d("getSavedQuestions data is collectd")
                ViewState.Success(it)
            }.asLiveData()
    }

    private fun getWrongAnswer(): LiveData<ViewState<List<QuestionInfo>>> {
        return getQuestionByWrongAnswerUseCase.invoke(Dispatchers.IO)
            .catch { error ->
                Timber.e("Error happened $error")
                ViewState.Error(("Error happened"))
            }.map {
                Timber.d("getQuestionsWithWrongAnswer data is collectd")
                ViewState.Success(it)
            }.asLiveData()
    }


    fun loadQuestions(type: CategoryType): LiveData<ViewState<List<QuestionInfo>>> {
        Timber.d("loadQuestions")
        selectedCategory = type
        return questionLiveData.getValue(type)
    }

    fun selectQuestion(position: Int) {
        Timber.d("selectQuestion")
        selectedIndex = position
        if (isSuccessState()) {
            val data =
                (questionLiveData[selectedCategory]?.value as? ViewState.Success<List<QuestionInfo>>)
            data?.data?.let {
                _selectedQuestion.value = it[position]
            }

        }
    }

    fun nextQuestion() {
        val next = selectedIndex + 1
        if (isSuccessState()) {
            val data =
                (questionLiveData[selectedCategory]?.value as? ViewState.Success<List<QuestionInfo>>)
            data?.data?.let {
                if (next < it.size) {
                    selectQuestion(next)
                }
            }
        }
    }

    fun previousQuestion() {
        val prev = selectedIndex - 1
        if (prev < 0) {
            return
        }
        selectQuestion(prev)
    }

    private fun isSuccessState(): Boolean {
        selectedCategory?.let {
            val data = questionLiveData[it]
            if (data?.value is ViewState.Success) {
                return true
            }
        }
        return false
    }
}

