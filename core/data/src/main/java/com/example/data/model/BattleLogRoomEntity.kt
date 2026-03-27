package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "battle_log")
data class BattleLogEntity(
    @PrimaryKey val id: Long,
    val hashtag: String,
    @ColumnInfo(name = "battle_time")
    val battleTime: String,
    val mode: String?,
    val result: String?,
    val duration: Int?,
    @ColumnInfo(name = "trophy_change")
    val trophyChange: Int?,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long,
)

@Entity(
    tableName = "battle_log_player",
    primaryKeys = ["battle_id", "team_index", "player_index"],
    foreignKeys = [
        ForeignKey(
            entity = BattleLogEntity::class,
            parentColumns = ["id"],
            childColumns = ["battle_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("battle_id")]
)
data class BattleLogPlayerEntity(
    @ColumnInfo(name = "battle_id")
    val battleId: Long,
    @ColumnInfo(name = "team_index")
    val teamIndex: Int,
    @ColumnInfo(name = "player_index")
    val playerIndex: Int,
    val tag: String?,
    val name: String?,
    @ColumnInfo(name = "brawler_id")
    val brawlerId: Int?,
    @ColumnInfo(name = "brawler_name")
    val brawlerName: String?,
    val power: Int?,
    val trophies: Int?,
)

data class BattleLogWithPlayers(
    @Embedded val battleLog: BattleLogEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "battle_id"
    )
    val players: List<BattleLogPlayerEntity>,
)
