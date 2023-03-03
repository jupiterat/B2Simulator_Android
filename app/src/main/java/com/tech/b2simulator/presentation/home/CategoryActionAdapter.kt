package com.tech.b2simulator.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.ViewCategoryBinding
import com.tech.b2simulator.domain.model.CategoryActionInfo

class CategoryActionAdapter(val context: Context) :
    RecyclerView.Adapter<CategoryActionAdapter.CategoryActionVH>() {
    var data = listOf<CategoryActionInfo>()
    private var mListener: CategoryActionAdapterItemClickedListener? = null

    class CategoryActionVH(val binding: ViewCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setItemClickedListener(listener: CategoryActionAdapterItemClickedListener?) {
        mListener = listener
    }

    interface CategoryActionAdapterItemClickedListener {
        fun onItemClicked(categoryId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryActionVH {
        val binding = ViewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        );
        return CategoryActionVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryActionVH, position: Int) {
        val category = data[position]
        with(holder) {
            binding.tvTitle.text = category.title
            binding.progressCount.text = category.progress.toString()
            binding.total.text = category.total.toString()
            binding.progress.progress =
                ((category.progress.toFloat() / category.total) * 100).toInt()
            binding.root.background = ContextCompat.getDrawable(context, R.drawable.bg1)
            binding.root.setOnClickListener {
                mListener?.onItemClicked(category.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}