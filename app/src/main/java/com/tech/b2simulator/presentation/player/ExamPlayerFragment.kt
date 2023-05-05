package com.tech.b2simulator.presentation.player

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tech.b2simulator.R
import com.tech.b2simulator.domain.common.ExamScoreType
import com.tech.b2simulator.presentation.common.AlertDialogFragment
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
                player?.pause()
                observeExamResult()
                showResult()
            }
        }
    }

    override fun getInterstitialAdId(): String {
        return getString(R.string.exam_list_interstitial_result_ads_id)
    }

    override fun shouldResetAd(): Boolean {
        return false
    }

    private fun showResult() {
        val score = examPlayerViewModel.examScore
        val title = getString(R.string.notice)
        val message = if (score >= ExamScoreType.PASS_SCORE) {
            String.format(getString(R.string.congrats_message), score, ExamScoreType.MAX)
        } else {
            String.format(getString(R.string.sorry_message), score, ExamScoreType.MAX)
        }

        findNavController().navigate(
            ExamPlayerFragmentDirections.actionNavigationExamPlayerToNavigationExamResult(
                title,
                message
            )
        )
    }

    private fun observeExamResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            AlertDialogFragment.KEY_RESULT
        )?.observe(viewLifecycleOwner) {
            if (it) {
                showInterstitialAds()
                removeExamResultObserver()
            }
        }
    }

    private fun removeExamResultObserver() {
        findNavController().currentBackStackEntry?.savedStateHandle?.remove<Boolean>(
            AlertDialogFragment.KEY_RESULT
        )
    }
}