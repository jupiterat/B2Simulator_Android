package com.tech.b2simulator.presentation.home

import com.tech.b2simulator.domain.model.CategoryActionInfo

data class CategoryListState(
    val isLoading: Boolean = false,
    val categoryList: List<com.tech.b2simulator.domain.model.CategoryActionInfo> = emptyList(),
    val error: String = ""
) {
}