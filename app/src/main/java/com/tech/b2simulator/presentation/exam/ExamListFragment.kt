package com.tech.b2simulator.presentation.exam

import ItemOffsetDecoration
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.*
import com.tech.b2simulator.R
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.databinding.FragmentExamListBinding
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.presentation.B2BaseFragment
import timber.log.Timber


class ExamListFragment : B2BaseFragment() {

    private val viewModel: ExamViewModel by activityViewModels()

    private var _binding: FragmentExamListBinding? = null

    private val binding get() = _binding!!

    private var examListAdapter: ExamListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.exam_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_info) {
            findNavController().navigate(ExamListFragmentDirections.actionNavigationExamToGuide())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

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
        setupAd()
        setupExamList()
    }

    private fun setupExamList() {
        if (examListAdapter == null) {
            examListAdapter = ExamListAdapter(requireContext())
        }

        examListAdapter!!.listener = object : ExamListAdapter.ExamClickedListener {
            override fun onExamClicked(position: Int, exam: ExamInfo) {
                viewModel.selectedExam(position)
                findNavController().navigate(ExamListFragmentDirections.actionNavigationExamToNavigationPlayer())
            }
        }

        binding.rcExam.layoutManager = GridLayoutManager(requireActivity(), 2)

        binding.rcExam.addItemDecoration(
            ItemOffsetDecoration(requireContext(), R.dimen.exam_item_space)
        )
        binding.rcExam.adapter = examListAdapter
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