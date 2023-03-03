package com.tech.b2simulator.presentation.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.usecase.GetExamUseCase
import com.tech.b2simulator.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val getExamUseCase: GetExamUseCase
) : BaseViewModel() {


    private val examLiveData: LiveData<ViewState<List<ExamInfo>>> = getExams()

    private fun getExams(): LiveData<ViewState<List<ExamInfo>>> {
        Timber.d("getExams")
        return getExamUseCase.invoke(
            Dispatchers.IO
        )
            .distinctUntilChanged()
            .catch { error ->
                Timber.e("Error happened $error")
                ViewState.Error(("Error happened"))
            }.map {
                ViewState.Success(it)
            }.asLiveData()
    }


    fun loadExams(): LiveData<ViewState<List<ExamInfo>>> {
        return examLiveData
    }
}

