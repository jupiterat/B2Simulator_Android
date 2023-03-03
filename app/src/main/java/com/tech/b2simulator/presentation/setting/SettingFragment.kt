package com.tech.b2simulator.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tech.b2simulator.databinding.FragmentSettingBinding
import com.tech.b2simulator.presentation.B2BaseFragment
import com.tech.common.views.BaseFragment

class SettingFragment : B2BaseFragment() {

    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUpViews() {
        TODO("Not yet implemented")
    }

    override fun observeData() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}