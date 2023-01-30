package com.tech.b2simulator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryLocation(@PrimaryKey val id: Int, val title: String)
