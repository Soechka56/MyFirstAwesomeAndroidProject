package com.example.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class GetBattleLogNavigationKey(
    val hashtag: String,
) : NavKey

@Serializable
data class BattleLogDetailsNavigationKey(
    val id: Long,
) : NavKey
