package com.tech.b2simulator.presentation.setting.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tech.b2simulator.databinding.FragmentNotificationSettingBinding
import com.tech.b2simulator.presentation.B2BaseFragment

class NotificationSettingFragment : B2BaseFragment() {


    private var _binding: FragmentNotificationSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNotificationSettingBinding.inflate(inflater, container, false)
        _binding = binding
        return binding.root
    }

    override fun setUpViews() {

    }

    override fun observeData() {

    }
}