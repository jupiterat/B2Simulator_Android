package com.tech.b2simulator.data.local

import androidx.room.Dao
import androidx.room.Query
import com.tech.b2simulator.data.local.entity.CategoryAction
import com.tech.b2simulator.data.local.entity.CategoryLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryAction")
    fun getCategoryAction(): Flow<List<CategoryAction>>

    @Query("SELECT * FROM CategoryLocation")
    fun getCategoryLocation(): Flow<List<CategoryLocation>>
}