package com.tech.b2simulator.data.mapper

import com.tech.b2simulator.data.local.entity.CategoryAction
import com.tech.b2simulator.data.local.entity.CategoryLocation
import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.b2simulator.domain.model.CategoryLocationInfo

fun CategoryAction.toCategoryActionInfo(): CategoryActionInfo {
    return CategoryActionInfo(id, title, hint)
}


fun CategoryLocation.toCategoryLocationInfo(): CategoryLocationInfo {
    return CategoryLocationInfo(id, title, 0)
}