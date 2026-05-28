package com.example.domain.usecase

import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository

// format hashtag: "R80LYJGC"
class SearchBattlesLogByHashtagQueryUseCase(
    private val repository: BattleLogRepository
) {
    suspend operator fun invoke(hashtag: String): BattleLogResource<List<BattleLogItemModel>> {

        // some domain logic example
        val cleanHashtag = hashtag.removePrefix("#").trim().uppercase()
        if (cleanHashtag.length != 8) {
            return BattleLogResource(
            data = emptyList(),
            source = BattleLogDataSourceModel.LOCAL,
            error = "length must be 8")
        }

        return  repository.searchByUserHashtag(cleanHashtag)
    }
}
