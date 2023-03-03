package com.tech.b2simulator.domain.model

import android.os.Parcelable
import com.tech.b2simulator.domain.common.ExamScoreType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamInfo(
    val id: Int,
    val title: String,
    var score: Int = -1
) : Parcelable {
    val isPassed = ExamScoreType.isPassed(score)
}