package com.example.data.mapper

import com.example.data.model.BattleLogEntity
import com.example.data.model.BattleLogPlayerEntity
import com.example.network.pojo.BattleLogResponseDto

class BattleLogMapper {

    fun mapBattleLogs(
        input: BattleLogResponseDto,
        hashtag: String,
        lastUpdated: Long,
    ): List<BattleLogEntity> {
        return input.items.orEmpty().mapIndexed { index, battle ->
            val trophyChange = battle.battle?.trophyChange

            BattleLogEntity(
                id = index.toLong(),
                hashtag = hashtag,
                battleTime = battle.battleTime.orEmpty(),
                mode = battle.event?.mode,
                result = trophyChange.toBattleResult() ?: battle.battle?.result,
                duration = battle.battle?.duration,
                trophyChange = trophyChange,
                lastUpdated = lastUpdated,
            )
        }
    }

    fun mapPlayers(input: BattleLogResponseDto): List<BattleLogPlayerEntity> {
        return buildList {
            input.items.orEmpty().forEachIndexed { battleIndex, battle ->
                battle.battle?.teams.orEmpty().forEachIndexed { teamIndex, team ->
                    team.forEachIndexed { playerIndex, player ->
                        add(
                            BattleLogPlayerEntity(
                                battleId = battleIndex.toLong(),
                                teamIndex = teamIndex,
                                playerIndex = playerIndex,
                                tag = player.tag,
                                name = player.name,
                                brawlerId = player.brawler?.id,
                                brawlerName = player.brawler?.name,
                                power = player.brawler?.power,
                                trophies = player.brawler?.trophies,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun Int?.toBattleResult(): String? {
        return when {
            this == null -> null
            this > 0 -> "victory"
            this < 0 -> "defeat"
            else -> "draw"
        }
    }
}
