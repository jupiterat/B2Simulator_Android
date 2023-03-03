package com.tech.b2simulator.presentation

import com.tech.common.views.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class B2BaseFragment : BaseFragment() {
    abstract fun setUpViews()
    abstract fun observeData()
}