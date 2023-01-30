package com.tech.b2simulator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tech.b2simulator.domain.model.QuestionInfo


@Entity
data class Questions(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val hint: String,
    val url: String,
    val validTime: String,
    val thumbnail: String,
    val groupByLocation: Int,
    val groupByAction: Int,
    val saved: Int,
) {
    fun toQuestionInfo(): QuestionInfo {
        return QuestionInfo(
            id,
            title,
            description,
            hint,
            url,
            validTime,
            thumbnail,
            groupByLocation,
            groupByAction,
            saved == 1
        )
    }
}
