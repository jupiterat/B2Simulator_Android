package com.tech.b2simulator.presentation.player

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tech.b2simulator.R
import com.tech.b2simulator.presentation.question.QuestionListViewModel

class QuestionPlayerFragment : PlayerFragment() {

    private val questionPlayerViewModel: QuestionPlayerViewModel by viewModels()
    private val questionListViewModel: QuestionListViewModel by activityViewModels()

    override fun getPlayerViewModel(): PlayerViewModel {
        return questionPlayerViewModel
    }

    override fun getDataViewModel(): PlayerDataViewModel {
        return questionListViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.player_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_save) {
//            playerViewModel.saveQuestion()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun setUpViews() {
        super.setUpViews()
    }

    override fun observeData() {
        super.observeData()
        getPlayerViewModel().selectedQuestion.observe(viewLifecycleOwner) {
            if (it.id % 10 == 0) {
                player?.pause()
                showInterstitialAds()
            }
        }
    }

    override fun getInterstitialAdId(): String {
        return getString(R.string.question_list_interstitial_result_ads_id)
    }

    override fun shouldResetAd(): Boolean {
        return true
    }
}