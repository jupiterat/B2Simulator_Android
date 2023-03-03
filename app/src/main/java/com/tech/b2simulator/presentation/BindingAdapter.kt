package com.tech.b2simulator.presentation

import android.os.Build
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("bindTextViewStyle")
fun TextView.bindTextViewStyle(resId: Int) {
    if (Build.VERSION.SDK_INT >= 23) {
        this.setTextAppearance(resId);
    } else {
        this.setTextAppearance(this.context, resId);
    }
}