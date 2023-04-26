package com.tech.b2simulator.presentation.setting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.b2simulator.R

class HeaderViewHolder : RecyclerView.ViewHolder {
    var tvTitle: TextView? = null

    constructor(view: View) : super(view) {
        tvTitle = view.findViewById(R.id.tvTitle)
    }
}