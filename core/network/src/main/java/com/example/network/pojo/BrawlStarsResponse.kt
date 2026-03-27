package com.example.network.pojo

// naming in JSON and the conventions Kotlin are the same =) ps: without @SerializedName()
data class BrawlStarsErrorResponse(
    val reason: String?, val message: String?, val type: String?
)

data class BattleLogResponseDto(
    val items: List<BattleDto>?
)

data class BattleDto(
    val battleTime: String?,
    val event: EventDto?,
    val battle: BattleInfoDto?
)

data class EventDto(
    val id: Int?,
    val mode: String?,
    val map: String?
)

data class BattleInfoDto(
    val result: String?,
    val duration: Int?,
    val trophyChange: Int?,
    val starPlayer: PlayerBattleDto?,
    val teams: List<List<PlayerBattleDto>>?
)

data class PlayerBattleDto(
    val tag: String?,
    val name: String?,
    val brawler: BrawlerBattleDto?
)

data class BrawlerBattleDto(
    val id: Int?,
    val name: String?,
    val power: Int?,
    val trophies: Int?
)