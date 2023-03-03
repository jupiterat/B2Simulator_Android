package com.tech.b2simulator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Exams(
    @PrimaryKey val id: Int,
    val title: String,
    val score: Int = -1
)
