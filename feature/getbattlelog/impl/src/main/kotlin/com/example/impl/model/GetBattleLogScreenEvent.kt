package com.example.impl.model

sealed class GetBattleLogScreenEvent {
    data class ShowSourceMessageRes(val messageResId: Int) : GetBattleLogScreenEvent()
}
