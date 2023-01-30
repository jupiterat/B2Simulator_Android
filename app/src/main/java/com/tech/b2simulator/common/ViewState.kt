package com.tech.b2simulator.common

sealed class ViewState<out R> {
    class Success<T>(val data: T) : ViewState<T>()
    class Error(val message: String) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}
