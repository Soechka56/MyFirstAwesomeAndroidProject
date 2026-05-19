package com.example.domain.usecase

import com.example.domain.model.BattleLogDetailsModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowDetailsBattleLogUseCase(
    private val repository: BattleLogRepository
){
    suspend operator fun invoke(id: Long): BattleLogResource<BattleLogDetailsModel>{
        return withContext(Dispatchers.IO) {
            repository.getBattleLogDetails(id)
        }
    }
}
