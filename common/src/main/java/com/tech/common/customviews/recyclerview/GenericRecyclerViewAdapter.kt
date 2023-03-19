package com.tech.common.customviews.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class GenericRecyclerViewAdapter<T, L : BaseRecyclerListener?, VH : BaseViewHolder<T, L>?> :
    RecyclerView.Adapter<VH> {
    private var items: MutableList<T>?


    var listener: L? = null
        private set
    private var layoutInflater: LayoutInflater

    constructor(context: Context?, listener: L?) {
        this.listener = listener
        items = ArrayList()
        layoutInflater = LayoutInflater.from(context)
    }


    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH


    override fun onBindViewHolder(holder: VH, position: Int) {
        if (items!!.size <= position) {
            return
        }
        val item = items!![position]
        holder?.onBind(item)
    }

    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }

    fun setItems(items: List<T>?) {
        setItems(items, true)
    }

    @SuppressLint("NotifyDataSetChanged")
    @Throws(IllegalArgumentException::class)
    fun setItems(items: List<T>?, notifyChanges: Boolean) {
        requireNotNull(items) { "Cannot set `null` item to the Recycler adapter" }
        this.items!!.clear()
        this.items!!.addAll(items)
        if (notifyChanges) {
            notifyDataSetChanged()
        }
    }

    fun updateItems(newItems: List<T>?) {
        setItems(newItems, false)
    }

    fun updateItems(newItems: List<T>?, diffCallback: DiffUtil.Callback?) {

        val result: DiffUtil.DiffResult? = diffCallback?.let { DiffUtil.calculateDiff(it, false) }
        result?.let {
            setItems(newItems, false)
            it.dispatchUpdatesTo(this)
        }
    }

    fun getItems(): List<T>? {
        return items
    }

    fun getItem(position: Int): T {
        return items!![position]
    }

    fun add(item: T?) {
        requireNotNull(item) { "Cannot add null item to the Recycler adapter" }
        items!!.add(item)
        notifyItemInserted(items!!.size - 1)
    }

    fun addToBeginning(item: T?) {
        requireNotNull(item) { "Cannot add null item to the Recycler adapter" }
        items!!.add(0, item)
        notifyItemInserted(0)
    }

    fun addAll(items: List<T>?) {
        requireNotNull(items) { "Cannot add `null` items to the Recycler adapter" }
        this.items!!.addAll(items)
        notifyItemRangeInserted(this.items!!.size - items.size, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items!!.clear()
        notifyDataSetChanged()
    }

    fun remove(item: T) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    fun setListener(listener: L?) {
        this.listener = listener
    }

    protected fun inflate(
        @LayoutRes layout: Int,
        @Nullable parent: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        return layoutInflater.inflate(layout, parent, attachToRoot)
    }

    protected fun inflate(@LayoutRes layout: Int, @Nullable parent: ViewGroup?): View {
        return inflate(layout, parent, false)
    }
}