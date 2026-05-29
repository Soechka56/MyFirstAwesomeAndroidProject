package com.example.impl.model

import androidx.compose.runtime.Immutable
import com.example.domain.model.BattleLogDetailsModel

@Immutable
data class GetBattleLogScreenState(
    val battleDetails: BattleLogDetailsModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
