package com.example.domain.repository

import com.example.domain.model.BattleLogDetailsModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource

interface BattleLogRepository {
    suspend fun searchByUserHashtag(hashtag: String): BattleLogResource<List<BattleLogItemModel>>
    suspend fun getBattleLogDetails(id: Long): BattleLogResource<BattleLogDetailsModel>
}
