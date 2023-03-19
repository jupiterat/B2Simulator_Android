package com.tech.common.customviews.recyclerview

interface OnRecyclerItemClickListener : BaseRecyclerListener {
    fun onItemClick(position: Int)
}