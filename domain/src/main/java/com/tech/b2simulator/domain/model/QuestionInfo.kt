package com.tech.b2simulator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class QuestionInfo(
    val id: Int,
    val title: String,
    val description: String,
    val hint: String,
    val url: String,
    val validTime: String,
    val thumbnail: String,
    val groupByLocation: Int,
    val groupByAction: Int,
    var saved: Boolean,
    var score: Int = -1
) : Parcelable
