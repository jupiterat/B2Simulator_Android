package com.tech.b2simulator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryAction(@PrimaryKey val id: Int, val title: String, var hint: String)