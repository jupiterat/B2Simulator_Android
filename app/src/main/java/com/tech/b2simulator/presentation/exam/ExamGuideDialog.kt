package com.tech.b2simulator.presentation.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.FragmentExamGuideBinding


class ExamGuideDialog : DialogFragment() {

    private var _binding: FragmentExamGuideBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamGuideBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ExamGuideAdapter(this)
        _binding?.viewPager?.let { pager ->
            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == pager.adapter!!.itemCount - 1) {
                        _binding?.closeButton?.visibility = View.VISIBLE
                    } else {
                        _binding?.closeButton?.visibility = View.GONE
                    }
                }
            })
            pager.adapter = adapter
            TabLayoutMediator(_binding!!.tabLayout, pager) { _, _ ->
            }.attach()
        }
        _binding?.closeButton?.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onResume() {
        // Get existing layout params for the window
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        // Call super onResume after sizing
        super.onResume()
    }

    class ExamGuideAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        val data = arrayListOf(
            Triple(R.drawable.intro1, R.string.exam_guide_title_1, R.string.exam_guide_content_1),
            Triple(R.drawable.intro5, R.string.exam_guide_title_2, R.string.exam_guide_content_2),
            Triple(R.drawable.intro10, R.string.exam_guide_title_3, R.string.exam_guide_content_3)
        )

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            val item = data[position]

            return ExamGuideItemFragment.makeInstance(item.first, item.second, item.third)
        }
    }

}