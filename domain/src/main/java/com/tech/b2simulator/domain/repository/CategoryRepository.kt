package com.tech.b2simulator.domain.repository

import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.b2simulator.domain.model.CategoryLocationInfo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getActionCategory(): Flow<List<CategoryActionInfo>>
    fun getLocationCategory(): Flow<List<CategoryLocationInfo>>
}