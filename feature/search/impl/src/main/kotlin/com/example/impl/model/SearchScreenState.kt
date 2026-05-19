package com.example.impl.model

import com.example.domain.model.BattleLogItemModel

data class SearchScreenState(
    val hashtag: String = "",
    val battles: List<BattleLogItemModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
