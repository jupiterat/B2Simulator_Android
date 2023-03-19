package com.tech.b2simulator.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tech.b2simulator.databinding.ViewCategoryBinding
import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.common.customviews.recyclerview.GenericRecyclerViewAdapter

class CategoryActionAdapter(val context: Context, listener: CategoryAdapterItemClickedListener?) :
    GenericRecyclerViewAdapter<CategoryActionInfo, CategoryAdapterItemClickedListener, CategoryActionVH>(
        context,
        listener
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryActionVH {
        val binding = ViewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryActionVH(context, binding, listener)
    }
}