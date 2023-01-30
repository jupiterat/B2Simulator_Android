package com.tech.b2simulator.domain.model


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
    val saved: Boolean,
)
