package com.tech.b2simulator.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.b2simulator.domain.usecase.GetActionCategoryUseCase
import com.tech.b2simulator.domain.usecase.GetLocationCategoryUseCase
import com.tech.b2simulator.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationCategoryUseCase: GetLocationCategoryUseCase,
    private val getActionCategoryUseCase: GetActionCategoryUseCase
) :
    BaseViewModel() {

    private val _categoryLocationLiveData = MutableLiveData<ViewState<List<CategoryLocationInfo>>>()
    val categoryLocationLiveData: LiveData<ViewState<List<CategoryLocationInfo>>>
        get() = _categoryLocationLiveData


    private val _categoryActionLiveData = MutableLiveData<ViewState<List<CategoryActionInfo>>>()
    val categoryActionLiveData: LiveData<ViewState<List<CategoryActionInfo>>>
        get() = _categoryActionLiveData

    init {
        getActionCategory()
        getLocationCategory()
    }

    private fun getActionCategory() {
        Timber.d("getActionCategory")
        _categoryActionLiveData.postValue(ViewState.Loading)

        viewModelScope.launch {
            getActionCategoryUseCase.invoke(Dispatchers.IO)
                .catch { error ->
                    Timber.e("Error happened $error")
                    _categoryActionLiveData.postValue(ViewState.Error("Error happened"))
                }
                .collect {
                    Timber.d("getActionCategoryUseCase data is collectd")
                    _categoryActionLiveData.postValue(ViewState.Success(it))
                }
        }
    }

    private fun getLocationCategory() {
        Timber.d("getLocationCategory")
        _categoryLocationLiveData.postValue(ViewState.Loading)

        viewModelScope.launch {
            getLocationCategoryUseCase.invoke(Dispatchers.IO)
                .catch { error ->
                    Timber.e("Error happened $error")
                    _categoryLocationLiveData.postValue(ViewState.Error("Error happened"))
                }
                .collect {
                    Timber.d("getActionCategoryUseCase data is collectd")
                    _categoryLocationLiveData.postValue(ViewState.Success(it))
                }
        }
    }
}