package com.tech.b2simulator.presentation.home

import com.tech.common.customviews.recyclerview.BaseRecyclerListener

interface CategoryAdapterItemClickedListener: BaseRecyclerListener {
    fun onItemClicked(categoryId: Int)
}