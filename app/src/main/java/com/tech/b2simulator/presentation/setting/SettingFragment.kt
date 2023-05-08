package com.tech.b2simulator.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.FragmentSettingBinding
import com.tech.b2simulator.presentation.B2BaseFragment
import com.tech.common.utils.CommonUtil
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import timber.log.Timber


class SettingFragment : B2BaseFragment(), SettingSection.ClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val settingViewModel: SettingViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    private val settingItems = arrayOf(
        Pair(
            R.string.general_setting,
            listOf(
                SettingItem(R.string.clear_data_setting, R.drawable.ic_baseline_delete_outline_24),
//                SettingItem(
//                    R.string.notification_setting,
//                    R.drawable.ic_baseline_notifications_none_24
//                ),
//                SettingItem(R.string.review_setting, R.drawable.ic_baseline_rate_review_24),
                SettingItem(R.string.share_setting, R.drawable.ic_baseline_ios_share_24),
            )
        ),
//        Pair(
//            R.string.payment_setting,
//            listOf(
//                SettingItem(R.string.membership_setting, R.drawable.ic_baseline_card_membership_24),
//                SettingItem(R.string.restore_setting, R.drawable.ic_baseline_restore_24)
//            )
//        ),
        Pair(
            R.string.policy_setting,
            listOf(
                SettingItem(R.string.privacy_setting, R.drawable.ic_baseline_privacy_tip_24),
                SettingItem(R.string.license_setting, R.drawable.ic_baseline_local_police_24)
            )
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        setUpViews()

        return _binding!!.root
    }

    override fun setUpViews() {
        setupReview()
        setupAd()
        setupList()
    }

    private fun setupList() {
        val sectionedAdapter = SectionedRecyclerViewAdapter()

        for (item in settingItems) {
            sectionedAdapter.addSection(
                SettingSection(
                    item.first,
                    item.second,
                    this
                )
            )
        }

        val recyclerView = binding?.rvSetting
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = sectionedAdapter
    }

    private fun setupAd() {
        val adRequest = AdRequest.Builder().build()
        _binding?.adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Timber.d("onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Timber.d("onAdFailedToLoad")
            }
        }
        _binding?.adView?.loadAd(adRequest)
    }

    override fun observeData() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemRootViewClicked(section: Int, itemAdapterPosition: Int) {
        when (section) {
            R.string.license_setting -> {
                findNavController().navigate(SettingFragmentDirections.actionSettingToWebview("file:///android_asset/license.html"))
            }
            R.string.privacy_setting -> {
                findNavController().navigate(SettingFragmentDirections.actionSettingToWebview("file:///android_asset/privacy.html"))
            }
            R.string.review_setting -> {
                startReviewFlow()
            }
            R.string.share_setting -> {
                val url = "https://play.google.com/store/apps/details?id=com.tech.b2simulator"
                CommonUtil.shareText(
                    requireContext(),
                    url
                )
            }
            R.string.notification_setting -> {
                findNavController().navigate(SettingFragmentDirections.actionSettingToNotificationSetting())
            }
            R.string.clear_data_setting -> {
                settingViewModel.clearData()
                showDataClearSuccessDialog()
            }
            else -> {
                Timber.d("no item selected")
            }
        }
    }

    private fun showDataClearSuccessDialog() {

        val title = getString(R.string.notice)
        val message = getString(R.string.message_clear_data_success)

        findNavController().navigate(
            SettingFragmentDirections.actionSettingToAlertDialog(
                title,
                message
            )
        )
    }
}