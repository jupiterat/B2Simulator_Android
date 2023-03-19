package com.tech.b2simulator.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tech.b2simulator.databinding.ViewCategoryBinding
import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.common.customviews.recyclerview.GenericRecyclerViewAdapter

class CategoryLocationAdapter(val context: Context, listener: CategoryAdapterItemClickedListener?) :
    GenericRecyclerViewAdapter<CategoryLocationInfo, CategoryAdapterItemClickedListener, CategoryLocationVH>(
        context,
        listener
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryLocationVH {
        val binding = ViewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryLocationVH(context, binding, listener)
    }


}