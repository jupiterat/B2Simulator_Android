package com.tech.common.customviews.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, L : BaseRecyclerListener?> : RecyclerView.ViewHolder {
    protected var listener: L? = null
        private set

    constructor(itemView: View) : super(itemView) {}
    constructor(context: Context, itemView: View, listener: L?) : super(itemView) {
        this.listener = listener
    }

    abstract fun onBind(item: T)

    fun onBind(item: T, payloads: List<Any?>?) {
        onBind(item)
    }
}