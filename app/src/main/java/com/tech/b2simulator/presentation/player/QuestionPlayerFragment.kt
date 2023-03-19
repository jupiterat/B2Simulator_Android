package com.tech.b2simulator.presentation.player

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tech.b2simulator.R
import com.tech.b2simulator.common.getQuestionScoreColor
import com.tech.b2simulator.presentation.question.QuestionListViewModel
import timber.log.Timber

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

    override fun observeData() {
        super.observeData()
        getPlayerViewModel().score.observe(viewLifecycleOwner) {
            Timber.d("score observe received $it")
            binding?.scoreType = it
            binding?.tvScore?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    getQuestionScoreColor(it.score)
                )
            )
        }
    }
}