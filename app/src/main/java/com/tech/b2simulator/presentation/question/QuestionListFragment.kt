package com.tech.b2simulator.presentation.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tech.b2simulator.databinding.FragmentQuestionListBinding
import com.tech.b2simulator.presentation.BaseFragment
import timber.log.Timber

class QuestionListFragment : BaseFragment() {
    private var _binding: FragmentQuestionListBinding? = null
    private val binding get() = _binding!!
    private val args: QuestionListFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentQuestionListBinding.inflate(inflater, container, false)
        setUpViews()
        observeData()
        return binding.root
    }

    override fun setUpViews() {
        Timber.d("categoryId: ${args.categoryId}")
    }

    override fun observeData() {

    }
}