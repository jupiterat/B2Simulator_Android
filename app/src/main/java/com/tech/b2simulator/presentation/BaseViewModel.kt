package com.tech.b2simulator.presentation

import androidx.lifecycle.ViewModel
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    init {
        Timber.tag(this.javaClass.simpleName).d("init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(this.javaClass.simpleName).d("onCleared")
    }
}