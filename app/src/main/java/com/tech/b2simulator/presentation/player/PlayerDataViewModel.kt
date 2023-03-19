package com.tech.b2simulator.presentation.player

import androidx.lifecycle.LiveData
import com.tech.b2simulator.domain.model.QuestionInfo

interface PlayerDataViewModel {
    fun getSelectedQuestion(): LiveData<QuestionInfo>
    fun nextQuestion()
    fun previousQuestion()
    fun selectQuestion(position: Int)
}