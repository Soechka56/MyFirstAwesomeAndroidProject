package com.example.domain.model

enum class BattleLogDataSourceModel {
    SERVER,
    LOCAL,
}

data class BattleLogResource<T>(
    val data: T,
    val source: BattleLogDataSourceModel,
    val error: String = ""
)
