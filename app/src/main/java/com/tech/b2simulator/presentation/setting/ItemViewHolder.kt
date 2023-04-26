package com.tech.b2simulator.presentation.setting

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.b2simulator.R


class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imgItem: ImageView? = null
    var tvItem: TextView? = null

    init {
        imgItem = view.findViewById(R.id.imgItem)
        tvItem = view.findViewById(R.id.tvItem)
    }
}