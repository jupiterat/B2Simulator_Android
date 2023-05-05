package com.tech.b2simulator.presentation.setting

import androidx.lifecycle.viewModelScope
import com.tech.b2simulator.domain.usecase.ClearDataUseCase
import com.tech.b2simulator.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val clearDataUseCase: ClearDataUseCase
) :
    BaseViewModel() {

    fun clearData() {
        viewModelScope.launch {
            clearDataUseCase.invoke(Dispatchers.IO)
        }
    }
}