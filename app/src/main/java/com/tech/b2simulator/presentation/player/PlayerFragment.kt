package com.tech.b2simulator.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SeekParameters
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tech.b2simulator.BuildConfig
import com.tech.b2simulator.BuildConfig.BASE_URL
import com.tech.b2simulator.R
import com.tech.b2simulator.common.getQuestionScoreColor
import com.tech.b2simulator.databinding.FragmentPlayerBinding
import com.tech.b2simulator.domain.common.QuestionScoreType
import com.tech.b2simulator.domain.model.QuestionInfo
import com.tech.b2simulator.presentation.B2BaseFragment
import com.tech.common.utils.ScreenUtils
import timber.log.Timber
import kotlin.math.roundToInt

abstract class PlayerFragment : B2BaseFragment() {
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private var controllerView: PlayerControlView? = null
    private var progressBar: DefaultTimeBar? = null
    private var progressContainer: ConstraintLayout? = null
    private var scoreRangeView: ConstraintLayout? = null
    private var flag: AppCompatImageView? = null
    private var btnBackward: AppCompatButton? = null
    private var btnForward: AppCompatButton? = null
    private var hintContainer: LinearLayoutCompat? = null
    private var mInterstitialAd: InterstitialAd? = null

    protected var resetBtn: ImageView? = null
    protected var binding: FragmentPlayerBinding? = null
    protected var player: ExoPlayer? = null

    abstract fun getPlayerViewModel(): PlayerViewModel
    abstract fun getDataViewModel(): PlayerDataViewModel
    abstract fun getInterstitialAdId(): String
    abstract fun shouldResetAd(): Boolean

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
        setupAd()
        setupInterstitialAds()
        binding?.fragment = this
        btnBackward = binding?.btnBackward
        btnForward = binding?.btnForward
        hintContainer = binding?.container
        initializePlayer()
    }

    private fun setupAd() {
        val adRequest = AdRequest.Builder().build()
        binding?.adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Timber.d("onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Timber.d("onAdFailedToLoad")
            }
        }
        binding?.adView?.loadAd(adRequest)
    }

    override fun observeData() {
        getDataViewModel().getSelectedQuestion().value?.let {
            setPlayerViewModelData(it)
        }
        getDataViewModel().getSelectedQuestion().observe(viewLifecycleOwner) {
            setPlayerViewModelData(it)
        }
        getPlayerViewModel().selectedQuestion.observe(viewLifecycleOwner) {
            Timber.e("selectedQuestion observe received")
            resetPlayer()
            setQuestionInfo(it)
            updatePlayerUrl(it.url)
        }

        getPlayerViewModel().flagPosition.observe(viewLifecycleOwner) { percent ->
            Timber.e("flagPosition observe received")
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
        getPlayerViewModel().nextQuestion()
    }

    fun previousQuestion() {
        getDataViewModel().previousQuestion()
        getPlayerViewModel().previousQuestion()
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

    private fun setupInterstitialAds() {
        Timber.d("registerInterstitialAdListener")
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            getInterstitialAdId(),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.d(adError.toString())
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Timber.d("Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    protected fun showInterstitialAds() {
        if (mInterstitialAd != null) {
            registerInterstitialAdListener()
            mInterstitialAd?.show(requireActivity())
        } else {
            Timber.d("The interstitial ad wasn't ready yet.")
        }
    }

    private fun registerInterstitialAdListener() {
        Timber.d("registerInterstitialAdListener")
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Timber.d("Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Timber.d("Ad dismissed fullscreen content.")

                mInterstitialAd = null
                if (shouldResetAd()) {
                    setupInterstitialAds()
                }
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                Timber.d("Ad failed to show")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Timber.d("Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Timber.d("Ad showed fullscreen content.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        releasePlayer()
    }

}