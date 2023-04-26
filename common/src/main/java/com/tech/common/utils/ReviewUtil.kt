package com.tech.common.utils

import android.app.Activity
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import timber.log.Timber

class ReviewUtil private constructor(context: Context) {
    /**
     * Call this method at app start etc to pre-cache the reviewInfo object to use to show
     * in-app review dialog later.
     */
    private var reviewManager: ReviewManager? = null
    private var reviewInfo: ReviewInfo? = null

    init {
        reviewManager = ReviewManagerFactory.create(context)
    }

    companion object {

        private var instance: ReviewUtil? = null

        @JvmStatic
        fun getInstance(context: Context): ReviewUtil =
            instance ?: synchronized(this) {
                instance ?: ReviewUtil(context).also { instance = it }
            }
    }

    fun getReviewInfo() {
        val manager = reviewManager?.requestReviewFlow()
        manager?.addOnCompleteListener { task: Task<ReviewInfo?> ->
            if (task.isSuccessful) {
                Timber.d("Get ReviewInfo successfully")
                reviewInfo = task.result
            } else {
                Timber.d("Get ReviewInfo un-successfully")
            }
        }
    }

    /**
     * Call this method when you want to show the in-app rating dialog
     */
    fun startReviewFlow(activity: Activity) {
        if (reviewInfo != null) {
            val flow = reviewManager!!.launchReviewFlow(activity, reviewInfo!!)
            flow.addOnCompleteListener {
                Timber.d("Review dialog is displayed")
            }
        } else {
            Timber.d("Can't display review dialog")
        }
    }
}