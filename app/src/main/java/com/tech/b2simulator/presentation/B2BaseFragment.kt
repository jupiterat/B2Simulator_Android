package com.tech.b2simulator.presentation

import com.tech.common.utils.ReviewUtil
import com.tech.common.views.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class B2BaseFragment : BaseFragment() {
    abstract fun setUpViews()
    abstract fun observeData()

    protected fun setupReview() {
        ReviewUtil.getInstance(requireContext()).getReviewInfo()
    }

    protected fun startReviewFlow() {
        ReviewUtil.getInstance(requireContext()).startReviewFlow(requireActivity())
    }
}