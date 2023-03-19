package com.tech.b2simulator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamInfo(
    val id: Int,
    val title: String,
    var score: Int = -1
) : Parcelable {
    var questions = listOf<QuestionInfo>()
}