package com.tech.b2simulator.domain.model

data class CategoryActionInfo(
    val id: Int,
    val title: String,
    val hint: String,
    var total: Int = 0,
    var progress: Int = 0
)

