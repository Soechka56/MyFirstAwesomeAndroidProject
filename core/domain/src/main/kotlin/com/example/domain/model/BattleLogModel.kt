package com.example.domain.model

// very simple battle log without extra models
data class BattleLogItemModel(
    val id: Long?,
    val battleTime: String,
    val mode: String?,
    val result: String?,
    val trophyChange: Int?,
)

data class BattleLogDetailsModel(
    val id: Long?,
    val battleTime: String,
    val mode: String?,
    val result: String?,
    val duration: Int?,
    val trophyChange: Int?,
    val teams: List<List<Player>>
) {
    data class Player(
        val id: Long?,
        val tag: String?,
        val name: String?,
        val brawlerId: Int?,
        val brawlerName: String?,
        val power: Int?,
        val trophies: Int?
    )

    companion object {
        val EMPTY = BattleLogDetailsModel(id = -1, battleTime = "", mode = "", result = "", duration = 0, trophyChange = null, teams = emptyList())
    }
}