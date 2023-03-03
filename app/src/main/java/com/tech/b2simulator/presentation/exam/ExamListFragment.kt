package com.tech.b2simulator.presentation.exam

import ItemOffsetDecoration
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tech.b2simulator.R
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.databinding.FragmentExamListBinding
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.presentation.B2BaseFragment
import timber.log.Timber


class ExamListFragment : B2BaseFragment() {

    private val viewModel: ExamViewModel by viewModels()

    private var _binding: FragmentExamListBinding? = null

    private val binding get() = _binding!!

    private var examListAdapter: ExamListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentExamListBinding.inflate(inflater, container, false)
        setUpViews()
        observeData()
        return binding.root
    }

    override fun setUpViews() {
        if (examListAdapter == null) {
            examListAdapter = ExamListAdapter(requireContext())
        }

        examListAdapter!!.listener = object : ExamListAdapter.ExamClickedListener {
            override fun onExamClicked(position: Int, exam: ExamInfo) {

            }
        }

        binding.rcExam.layoutManager = GridLayoutManager(requireActivity(), 2)

        binding.rcExam.addItemDecoration(
            ItemOffsetDecoration(requireContext(), R.dimen.exam_item_space)
        )
        binding.rcExam.adapter = examListAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeData() {
        viewModel.loadExams().observe(viewLifecycleOwner) {

            when (it) {
                is ViewState.Success -> {
                    Timber.d("loadExams received Success")
                    examListAdapter?.data = it.data
                    examListAdapter?.notifyDataSetChanged()
                }
                is ViewState.Loading -> {
                    Timber.d("loadExams received Loading")
                }
                is ViewState.Error -> {
                    Timber.d("loadExams received Error")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}