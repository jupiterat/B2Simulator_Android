package com.tech.b2simulator.presentation.home

import android.content.Context
import androidx.core.content.ContextCompat
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.ViewCategoryBinding
import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.common.customviews.recyclerview.BaseViewHolder

class CategoryLocationVH(
    val context: Context,
    val binding: ViewCategoryBinding,
    listener: CategoryAdapterItemClickedListener?
) :
    BaseViewHolder<CategoryLocationInfo, CategoryAdapterItemClickedListener>(
        context,
        binding.root,
        listener
    ) {
    override fun onBind(item: CategoryLocationInfo) {
        binding.tvTitle.text = item.title
        binding.total.text = item.total.toString()
        binding.progressCount.text = item.progress.toString()
        binding.progress.progress =
            ((item.progress.toFloat() / item.total) * 100).toInt()
        binding.root.background = ContextCompat.getDrawable(context, R.drawable.bg2)
        listener?.run {
            itemView.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }
}