package com.tech.b2simulator.presentation.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tech.b2simulator.domain.common.QuestionScoreType
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.domain.usecase.SaveQuestionScoreUseCase
import com.tech.b2simulator.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val saveQuestionScoreUseCase: SaveQuestionScoreUseCase
) : BaseViewModel() {
    private val _selectedQuestion = MutableLiveData<QuestionInfo>()
    val selectedQuestion: LiveData<QuestionInfo>
        get() = _selectedQuestion

    fun setCurrentQuestion(question: QuestionInfo) {
        _selectedQuestion.value = question
    }

    private val _fragPosition = MutableLiveData<Float>()
    val flagPosition: LiveData<Float>
        get() = _fragPosition

    private val _scoreRangePosition = MutableLiveData<Pair<Float, Float>>()
    val scoreRangePosition: LiveData<Pair<Float, Float>>
        get() = _scoreRangePosition

    private val _score = MutableLiveData(QuestionScoreType.UNDEFINED)
    val score: LiveData<QuestionScoreType>
        get() = _score

    fun spaceClicked(duration: Long, current: Long) {
        Timber.d("spaceClicked with duration: $duration and current: $current")
        calculateFlagPosition(duration, current)
        val ranges = selectedQuestion.value?.validTime?.split('-')
        if (ranges?.size == 2) {
            val start = ranges[0].toFloat()
            val end = ranges[1].toFloat()
            Timber.d("score range starts at $start - end at $end")

            calculateScoreRangePosition(duration, start * 1000, end * 1000)

            calculateScore(current, start * 1000, end * 1000)
        }
    }

    private fun calculateFlagPosition(duration: Long, current: Long) {
        _fragPosition.value = (current.toFloat() / duration)
    }

    private fun calculateScoreRangePosition(duration: Long, start: Float, end: Float) {
        val startPercent = start / duration
        val endPercent = end / duration

        _scoreRangePosition.value = Pair(startPercent, endPercent)
    }

    private fun calculateScore(current: Long, start: Float, end: Float) {
        if (current < start || current > end) {
            _score.value = QuestionScoreType.ZERO
            updateScore(QuestionScoreType.ZERO.score)
            return
        }
        val rangePerScore = (end - start) / 5
        for (i in 1..5) {
            if (current <= (start + rangePerScore * i)) {
                val score = QuestionScoreType.fromInt(6 - i)
                _score.value = score
                updateScore(score.score)
                return
            }
        }
    }

    private fun updateScore(score: Int) {
        viewModelScope.launch {
            val question = selectedQuestion.value
            question?.let {
                it.score = score
                saveQuestionScoreUseCase.invoke(it)
            }
        }
    }

    fun saveQuestion() {
        viewModelScope.launch {
            val question = selectedQuestion.value
            question?.let {
                it.saved = true
                saveQuestionScoreUseCase.invoke(it)
            }
        }
    }
}