package com.tech.b2simulator.presentation.player

import androidx.lifecycle.viewModelScope
import com.tech.b2simulator.domain.usecase.SaveQuestionScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionPlayerViewModel @Inject constructor(private val saveQuestionScoreUseCase: SaveQuestionScoreUseCase) :
    PlayerViewModel() {
    override fun updateScore(score: Int) {
        viewModelScope.launch {
            val question = selectedQuestion.value
            question?.let {
                it.score = score
                saveQuestionScoreUseCase.invoke(it)
            }
        }
    }

}