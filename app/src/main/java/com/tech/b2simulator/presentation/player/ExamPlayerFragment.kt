package com.tech.b2simulator.presentation.player

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tech.b2simulator.presentation.exam.ExamViewModel
import timber.log.Timber

class ExamPlayerFragment : PlayerFragment() {
    private val examPlayerViewModel: ExamPlayerViewModel by viewModels()
    private val viewModel: ExamViewModel by activityViewModels()

    override fun getPlayerViewModel(): PlayerViewModel {
        return examPlayerViewModel
    }

    override fun getDataViewModel(): PlayerDataViewModel {
        return viewModel
    }

    override fun setUpViews() {
        super.setUpViews()
        resetBtn?.visibility = View.GONE
    }

    override fun observeData() {
        super.observeData()
        getPlayerViewModel().score.observe(viewLifecycleOwner) {
            Timber.d("score observe received $it")
            if (viewModel.isLastQuestion()) {
                examPlayerViewModel.saveScore(viewModel.getSelectedExam())
            }
        }
    }

}