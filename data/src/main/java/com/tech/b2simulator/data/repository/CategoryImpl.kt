package com.tech.b2simulator.data.repository

import com.tech.b2simulator.data.local.CategoryDao
import com.tech.b2simulator.data.mapper.toCategoryActionInfo
import com.tech.b2simulator.data.mapper.toCategoryLocationInfo
import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.b2simulator.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class CategoryImpl @Inject constructor(private val dao: CategoryDao) : CategoryRepository {

    override fun getActionCategory(): Flow<List<CategoryActionInfo>> {
        Timber.d("getCategoryAction")
        return dao.getCategoryAction()
            .catch { error ->
                Timber.e("getActionCategory got error ${error.message}")
            }
            .map {
                it.map { item ->
                    item.toCategoryActionInfo()
                }
            }
    }

    override fun getLocationCategory(): Flow<List<CategoryLocationInfo>> {
        Timber.d("getLocationCategory")
        return dao.getCategoryLocation()
            .catch { error ->
                Timber.e("getLocationCategory got error ${error.message}")
            }
            .map {
                Timber.d("getLocationCategory mapping")
                it.map { item ->
                    item.toCategoryLocationInfo()
                }
            }
    }
}