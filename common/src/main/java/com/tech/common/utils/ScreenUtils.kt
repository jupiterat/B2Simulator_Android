package com.tech.common.utils

import android.content.Context

object ScreenUtils {
    fun dimenInPixel(context: Context, dp: Int): Int {
        return context.resources.getDimensionPixelSize(dp)
    }
}