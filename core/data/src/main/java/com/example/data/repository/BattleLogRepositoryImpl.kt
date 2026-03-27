package com.example.data.repository

import com.example.data.mapper.BattleLogMapper
import com.example.data.model.BattleLogDao
import com.example.data.model.BattleLogWithPlayers
import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogDetailsModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository
import com.example.network.BrawlStarsApi
import com.example.network.error.GeneralExceptionHandler
import com.example.network.error.runCatching
import com.example.network.error.impl.BrawlStarsExceptionHandlerImpl

class BattleLogRepositoryImpl(
    private val brawlStarsApi: BrawlStarsApi,
    private val battleLogDao: BattleLogDao,
    private val battleLogMapper: BattleLogMapper,
    private val generalExceptionHandler: GeneralExceptionHandler = BrawlStarsExceptionHandlerImpl(),
) : BattleLogRepository {

    override suspend fun searchByUserHashtag(hashtag: String): BattleLogResource<List<BattleLogItemModel>> {
        return brawlStarsApi.runCatching(generalExceptionHandler) {
            val clearHashtag = hashtag.removePrefix("#")
            val cachedBattleLogs = battleLogDao.getBattleLogsByHashtag(clearHashtag)

            if (shouldReadFromCache(cachedBattleLogs)) {
                return@runCatching BattleLogResource(
                    data = cachedBattleLogs.map(::toSimpleModel),
                    source = BattleLogDataSourceModel.LOCAL,
                )
            }

            refreshCache(clearHashtag)

            BattleLogResource(
                data = battleLogDao.getBattleLogsByHashtag(clearHashtag).map(::toSimpleModel),
                source = BattleLogDataSourceModel.SERVER,
            )
        }
    }


    override suspend fun getBattleLogDetails(id: Long): BattleLogResource<BattleLogDetailsModel> {
        return battleLogDao.runCatching(generalExceptionHandler) {
            battleLogDao.getById(id)?.let { battleLog ->
                BattleLogResource(
                    data = toDetailModel(battleLog),
                    source = BattleLogDataSourceModel.LOCAL,
                )
            } ?: error("battle log item not found")
        }
    }

    // check time last update
    private fun shouldReadFromCache(cachedBattleLogs: List<BattleLogWithPlayers>): Boolean {
        val firstBattleLog = cachedBattleLogs.firstOrNull() ?: return false
        return System.currentTimeMillis() - firstBattleLog.battleLog.lastUpdated <= CACHE_LIFETIME_MILLIS
    }

    private suspend fun refreshCache(hashtag: String) {
        val response = brawlStarsApi.getBattleLog(hashtag)
        val lastUpdated = System.currentTimeMillis()
        val battleLogs = battleLogMapper.mapBattleLogs(
            input = response,
            hashtag = hashtag,
            lastUpdated = lastUpdated,
        )
        val players = battleLogMapper.mapPlayers(response)

        battleLogDao.clearAll()
        if (battleLogs.isNotEmpty()) {
            battleLogDao.insertBattleLogs(battleLogs)
        }
        if (players.isNotEmpty()) {
            battleLogDao.insertPlayers(players)
        }
    }

    
    // mappers, domain needs two models, in Room only one
    private fun toSimpleModel(battleLog: BattleLogWithPlayers): BattleLogItemModel {
        return BattleLogItemModel(
            id = battleLog.battleLog.id,
            battleTime = battleLog.battleLog.battleTime,
            mode = battleLog.battleLog.mode,
            result = battleLog.battleLog.result,
            trophyChange = battleLog.battleLog.trophyChange,
        )
    }

    private fun toDetailModel(battleLog: BattleLogWithPlayers): BattleLogDetailsModel {
        return BattleLogDetailsModel(
            id = battleLog.battleLog.id,
            battleTime = battleLog.battleLog.battleTime,
            mode = battleLog.battleLog.mode,
            result = battleLog.battleLog.result,
            duration = battleLog.battleLog.duration,
            trophyChange = battleLog.battleLog.trophyChange,
            teams = battleLog.players
                .sortedWith(compareBy({ it.teamIndex }, { it.playerIndex }))
                .groupBy { it.teamIndex }
                .values
                .map { players ->
                    players.map { player ->
                        BattleLogDetailsModel.Player(
                            id = null,
                            tag = player.tag,
                            name = player.name,
                            brawlerId = player.brawlerId,
                            brawlerName = player.brawlerName,
                            power = player.power,
                            trophies = player.trophies,
                        )
                    }
                },
        )
    }

    private companion object {
        const val CACHE_LIFETIME_MILLIS = 15_000L
    }
}
