package com.tech.b2simulator.presentation.player

import androidx.lifecycle.viewModelScope
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.usecase.SaveExamScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamPlayerViewModel @Inject constructor(private val saveExamScoreUseCase: SaveExamScoreUseCase) :
    PlayerViewModel() {
    private var examScore: Int = 0

    override fun updateScore(score: Int) {
        examScore += score
    }

    fun saveScore(selectedExam: ExamInfo?) {
        viewModelScope.launch {
            selectedExam?.let {
                it.score = examScore
                saveExamScoreUseCase.invoke(it)
            }
        }
    }
}