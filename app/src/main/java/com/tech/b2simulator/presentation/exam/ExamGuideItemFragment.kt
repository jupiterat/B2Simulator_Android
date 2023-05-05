package com.tech.b2simulator.presentation.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tech.b2simulator.databinding.FragmentExamGuideItemBinding
import com.tech.common.views.BaseFragment

class ExamGuideItemFragment : BaseFragment() {

    private var _binding: FragmentExamGuideItemBinding? = null

    companion object {
        val KEY_IMAGE = "image"
        val KEY_TITLE = "title"
        val KEY_CONTENT = "content"

        fun makeInstance(imageRes: Int, titleRes: Int, contentRes: Int): ExamGuideItemFragment {
            val fragment = ExamGuideItemFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_IMAGE, imageRes)
                putInt(KEY_TITLE, titleRes)
                putInt(KEY_CONTENT, contentRes)
            }
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamGuideItemBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(KEY_IMAGE)?.let {
            _binding?.imageView?.setImageResource(it)
        }

        arguments?.getInt(KEY_TITLE)?.let {
            _binding?.title?.text = getString(it)
        }

        arguments?.getInt(KEY_CONTENT)?.let {
            _binding?.content?.text = getString(it)
        }
    }
}