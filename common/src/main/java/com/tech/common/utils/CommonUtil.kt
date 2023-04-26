package com.tech.common.utils

import android.content.Context
import android.content.Intent


object CommonUtil {
    fun shareText(context: Context, subject: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            subject
        )
        context.startActivity(Intent.createChooser(intent, null))
    }
}