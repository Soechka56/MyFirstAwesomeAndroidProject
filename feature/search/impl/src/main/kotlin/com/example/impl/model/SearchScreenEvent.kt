package com.example.impl.model

sealed class SearchScreenEvent {
    data class ShowSourceMessage(val messageResId: Int) : SearchScreenEvent()
}
