package com.tech.b2simulator.presentation.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.usecase.GetExamUseCase
import com.tech.b2simulator.presentation.BaseViewModel
import com.tech.b2simulator.presentation.player.PlayerDataViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val getExamUseCase: GetExamUseCase
) : BaseViewModel(), PlayerDataViewModel {
    private var selectedQuestionIndex = -1
    private var selectedExamIndex = -1
    private val _selectedQuestion = MutableLiveData<QuestionInfo>()

    private val _noMoreData = MutableLiveData(false)
    val noMoreData: LiveData<Boolean>
        get() = _noMoreData

    private val examLiveData: LiveData<ViewState<List<ExamInfo>>> by lazy { getExams() }

    private fun getExams(): LiveData<ViewState<List<ExamInfo>>> {
        Timber.d("getExams")
        return getExamUseCase.invoke(Dispatchers.IO)
            .distinctUntilChanged()
            .catch { error ->
                Timber.e("Error happened $error")
                ViewState.Error(("Error happened"))
            }.map {
                ViewState.Success(it)
            }.asLiveData()
    }


    fun loadExams(): LiveData<ViewState<List<ExamInfo>>> {
        return examLiveData
    }

    override fun getSelectedQuestion(): LiveData<QuestionInfo> {
        return _selectedQuestion
    }

    override fun nextQuestion() {
        val next = selectedQuestionIndex + 1
        selectQuestion(next)
    }

    override fun previousQuestion() {
        val prev = selectedQuestionIndex - 1
        if (prev < 0) {
            return
        }
        selectQuestion(prev)
    }

    fun selectedExam(position: Int) {
        selectedExamIndex = position
        selectQuestion(0)
    }

    fun getSelectedExam(): ExamInfo? {
        val exams = examLiveData.value as? ViewState.Success<List<ExamInfo>>
        exams?.data?.let {
            return it[selectedExamIndex]
        }
        return null
    }

    /**
     * position of exams
     */
    /**
     * position of exams
     */
    override fun selectQuestion(position: Int) {
        //selected question index always start by 0
        getSelectedExam()?.let {
            val questions = it.questions
            if (position < questions.size) {
                selectedQuestionIndex = position
                _selectedQuestion.value = questions[selectedQuestionIndex]
            } else {
                Timber.d("No more data")

            }
        }
    }

    fun isLastQuestion(): Boolean {
        getSelectedExam()?.questions?.let {
            return selectedQuestionIndex == it.size - 1
        }
        return false
    }

}

