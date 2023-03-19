package com.tech.b2simulator.presentation.home

import android.content.Context
import androidx.core.content.ContextCompat
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.ViewCategoryBinding
import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.common.customviews.recyclerview.BaseViewHolder

class CategoryActionVH(
    val context: Context,
    val binding: ViewCategoryBinding,
    listener: CategoryAdapterItemClickedListener?
) :
    BaseViewHolder<CategoryActionInfo, CategoryAdapterItemClickedListener>(
        context,
        binding.root,
        listener
    ) {
    override fun onBind(item: CategoryActionInfo) {
        binding.tvTitle.text = item.title
        binding.total.text = item.total.toString()
        binding.progressCount.text = item.progress.toString()
        binding.progress.progress =
            ((item.progress.toFloat() / item.total) * 100).toInt()
        binding.root.background = ContextCompat.getDrawable(context, R.drawable.bg1)
        listener?.run {
            itemView.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }
}