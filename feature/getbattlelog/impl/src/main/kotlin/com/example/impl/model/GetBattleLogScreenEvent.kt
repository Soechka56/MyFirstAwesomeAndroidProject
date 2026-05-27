package com.example.impl.model

import androidx.compose.runtime.Immutable

sealed class GetBattleLogScreenEvent {
    @Immutable
    data class ShowSourceMessageRes(val messageResId: Int) : GetBattleLogScreenEvent()
}
