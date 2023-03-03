package com.tech.b2simulator.presentation.question

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tech.b2simulator.R
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.databinding.FragmentQuestionListBinding
import com.tech.b2simulator.domain.common.CategoryType
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.presentation.B2BaseFragment
import timber.log.Timber

class QuestionListFragment : B2BaseFragment() {
    private var _binding: FragmentQuestionListBinding? = null
    private val binding get() = _binding!!
    private val args: QuestionListFragmentArgs by navArgs()
    private val questionListViewModel: QuestionListViewModel by activityViewModels()
    private var questionListAdapter: QuestionListAdapter? = null

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
        if (questionListAdapter == null) {
            questionListAdapter = QuestionListAdapter(requireContext())
        }

        questionListAdapter!!.listener = object : QuestionListAdapter.QuestionClickedListener {
            override fun onQuestionClicked(position: Int, question: QuestionInfo) {
                questionListViewModel.selectQuestion(position)
                findNavController().navigate(
                    QuestionListFragmentDirections.actionNavigationQuestionToNavigationPlayer()
                )
            }
        }

        binding.rvQuestionList.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.rvQuestionList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvQuestionList.adapter = questionListAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeData() {
        questionListViewModel.loadQuestions(args.questionType).observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Timber.d("questionListLiveData received Success")

                    if (it.data.isEmpty()) {
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.rvQuestionList.visibility = View.GONE
                        if (args.questionType == CategoryType.SAVED) {
                            binding.tvNoData.text = getString(R.string.no_saved_question_message)
                        } else if (args.questionType == CategoryType.WRONG) {
                            binding.tvNoData.text = getString(R.string.no_wrong_question_message)
                        }
                    } else {
                        binding.tvNoData.visibility = View.GONE
                        binding.rvQuestionList.visibility = View.VISIBLE
                        questionListAdapter?.data = it.data
                        questionListAdapter?.notifyDataSetChanged()
                    }
                }
                is ViewState.Loading -> {
                    Timber.d("questionListLiveData received Loading")
                }
                is ViewState.Error -> {
                    Timber.d("questionListLiveData received Error")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        questionListAdapter?.listener = null
    }
}