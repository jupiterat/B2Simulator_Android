package com.tech.b2simulator.presentation.setting

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.tech.b2simulator.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import timber.log.Timber


class SettingSection(
    @StringRes val title: Int,
    private val list: List<SettingItem>,
    private val clickListener: ClickListener
) :
    Section(
        SectionParameters.builder()
            .itemResourceId(R.layout.section_ex1_item)
            .headerResourceId(R.layout.section_ex1_header)
            .build()
    ) {
    override fun getContentItemsTotal(): Int {
        return list.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder: ItemViewHolder = holder as ItemViewHolder
        val item: SettingItem = list[position]
        itemHolder.tvItem?.setText(item.title)
        itemHolder.imgItem?.setImageResource(item.icon)
        itemHolder.itemView.setOnClickListener {
            Timber.d("section ${this.title} ")
            clickListener.onItemRootViewClicked(
                item.title,
                itemHolder.absoluteAdapterPosition
            )
        }
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return HeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder: HeaderViewHolder = holder as HeaderViewHolder
        headerHolder.tvTitle?.setText(title)
    }

    interface ClickListener {
        fun onItemRootViewClicked(section: Int, itemAdapterPosition: Int)
    }
}