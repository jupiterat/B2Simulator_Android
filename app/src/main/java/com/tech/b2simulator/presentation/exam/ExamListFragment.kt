package com.tech.b2simulator.presentation.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tech.b2simulator.databinding.FragmentExamListBinding
import com.tech.b2simulator.presentation.BaseFragment


class ExamListFragment : BaseFragment() {

    private var _binding: FragmentExamListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentExamListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun setUpViews() {
        val textView: TextView = binding.textDashboard
    }

    override fun observeData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}