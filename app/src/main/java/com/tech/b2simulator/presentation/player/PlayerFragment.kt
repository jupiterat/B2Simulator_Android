package com.tech.b2simulator.presentation.player

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SeekParameters
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.PlayerControlView
import com.tech.b2simulator.BuildConfig
import com.tech.b2simulator.BuildConfig.BASE_URL
import com.tech.b2simulator.R
import com.tech.b2simulator.databinding.FragmentPlayerBinding
import com.tech.b2simulator.domain.common.QuestionScoreType
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.presentation.B2BaseFragment
import com.tech.common.utils.ScreenUtils
import timber.log.Timber
import kotlin.math.roundToInt

abstract class PlayerFragment : B2BaseFragment() {

    protected var binding: FragmentPlayerBinding? = null
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private var controllerView: PlayerControlView? = null
    private var progressBar: DefaultTimeBar? = null
    private var progressContainer: ConstraintLayout? = null
    private var scoreRangeView: ConstraintLayout? = null
    private var flag: AppCompatImageView? = null

    var resetBtn: ImageView? = null

    abstract fun getPlayerViewModel(): PlayerViewModel
    abstract fun getDataViewModel(): PlayerDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentBinding = FragmentPlayerBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        setUpViews()
        observeData()
        return fragmentBinding.root
    }

    override fun setUpViews() {
        binding?.fragment = this
        initializePlayer()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun observeData() {
        getDataViewModel().getSelectedQuestion().value?.let {
            setPlayerViewModelData(it)
        }
        getDataViewModel().getSelectedQuestion().observe(viewLifecycleOwner) {
            setPlayerViewModelData(it)
        }
        getPlayerViewModel().selectedQuestion.observe(viewLifecycleOwner) {
            resetPlayer()
            setQuestionInfo(it)
            updatePlayerUrl(it.url)
        }

        getPlayerViewModel().flagPosition.observe(viewLifecycleOwner) { percent ->
            Timber.e("flagPosition received")
            if (flag != null && progressBar != null) {
                flag!!.apply {
                    x = progressBar!!.x + (progressBar!!.width * percent)
                    visibility = View.VISIBLE
                }
            }
        }

        getPlayerViewModel().scoreRangePosition.observe(viewLifecycleOwner) {
            val params = scoreRangeView?.layoutParams

            Timber.e("scoreRangePosition received")


            scoreRangeView?.apply {
                x = progressBar!!.x + (progressBar!!.width * it.first)
                params?.width =
                    ((progressBar!!.x + (progressBar!!.width * it.second)) - x).roundToInt()
                layoutParams = params
                visibility = View.VISIBLE
            }
        }
    }

    private fun setPlayerViewModelData(data: QuestionInfo?) {
        data?.let {
            getPlayerViewModel().setCurrentQuestion(it)
        }
    }

    private fun setQuestionInfo(question: QuestionInfo?) {
        binding?.question = question
    }

    private fun initializePlayer() {
        val playerView = binding?.playerView
        playerView?.let {
            player = ExoPlayer.Builder(requireContext())
                .build()
                .also { exoPlayer ->
                    val mediaItem =
                        MediaItem.fromUri(BASE_URL + { getPlayerViewModel().selectedQuestion.value?.url })
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC)
                    exoPlayer.playWhenReady = playWhenReady
                    exoPlayer.seekTo(currentItem, playbackPosition)
                    exoPlayer.prepare()
                    it.player = exoPlayer
                }
            it.setShowNextButton(false)
            it.setShowPreviousButton(false)
            it.setShowFastForwardButton(false)
            it.setShowRewindButton(false)
            controllerView =
                it.findViewById(com.google.android.exoplayer2.R.id.exo_controller)
            controllerView?.let { controllerView ->
                progressBar =
                    controllerView.findViewById(com.google.android.exoplayer2.R.id.exo_progress)
                progressContainer = controllerView.findViewById(R.id.progressContainer)
                flag = controllerView.findViewById(R.id.flag)
                scoreRangeView = controllerView.findViewById(R.id.constraintLayout)
                resetBtn = controllerView.findViewById(R.id.exo_reset)
                resetBtn?.setOnClickListener {
                    resetPlayer()
                }
            }
        }
        progressBar?.hideScrubber(true)
    }

    private fun resetPlayer() {
        Timber.d("resetPlayer")
        scoreRangeView?.apply {
            visibility = View.GONE
        }
        flag?.apply {
            visibility = View.GONE
        }
        binding?.scoreType = QuestionScoreType.UNDEFINED
        player?.seekTo(0)
        player?.playWhenReady = true
    }

    private fun updatePlayerUrl(url: String) {
        player?.removeMediaItem(0)
        val mediaItem =
            MediaItem.fromUri(BuildConfig.BASE_URL + url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    fun nextQuestion() {
        getDataViewModel().nextQuestion()
    }

    fun previousQuestion() {
        getDataViewModel().previousQuestion()
    }

    fun onSpaceClicked() {
        progressContainer?.let {
            val constraintSet = ConstraintSet()
            constraintSet.clone(it)
            constraintSet.connect(
                R.id.constraintLayout,
                ConstraintSet.TOP,
                com.google.android.exoplayer2.ui.R.id.exo_progress,
                ConstraintSet.TOP,
                0
            )

            constraintSet.connect(
                R.id.constraintLayout,
                ConstraintSet.BOTTOM,
                com.google.android.exoplayer2.ui.R.id.exo_progress,
                ConstraintSet.BOTTOM,
                ScreenUtils.dimenInPixel(requireContext(), R.dimen.progress_margin_range)
            )

            constraintSet.connect(
                R.id.flag,
                ConstraintSet.TOP,
                com.google.android.exoplayer2.ui.R.id.exo_progress,
                ConstraintSet.TOP,
                0
            )

            constraintSet.connect(
                R.id.flag,
                ConstraintSet.BOTTOM,
                com.google.android.exoplayer2.ui.R.id.exo_progress,
                ConstraintSet.BOTTOM,
                ScreenUtils.dimenInPixel(requireContext(), R.dimen.progress_margin_flag)
            )

            constraintSet.applyTo(it)
        }

        player?.let { player ->
            getPlayerViewModel().spaceClicked(player.duration, player.currentPosition)

        }
    }


    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        releasePlayer()
    }

}