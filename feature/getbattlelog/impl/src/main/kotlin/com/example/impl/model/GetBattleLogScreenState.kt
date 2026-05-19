package com.example.impl.model

import com.example.domain.model.BattleLogDetailsModel

data class GetBattleLogScreenState(
    val battleDetails: BattleLogDetailsModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
