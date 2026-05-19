package com.example.domain.usecase

import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchBattlesLogByHashtagQueryUseCase(
    private val repository: BattleLogRepository
) {
    suspend operator fun invoke(hashtag: String): BattleLogResource<List<BattleLogItemModel>> {
        return withContext(Dispatchers.IO) {
            repository.searchByUserHashtag(hashtag)
        }
    }
}
