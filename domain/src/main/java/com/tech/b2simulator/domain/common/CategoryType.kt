package com.tech.b2simulator.domain.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CategoryType : Parcelable {
    class ACTION(val id: Int) : CategoryType()
    class LOCATION(val id: Int) : CategoryType()
    object WRONG : CategoryType()
    object SAVED : CategoryType()
}