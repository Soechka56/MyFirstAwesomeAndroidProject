package com.example.domain.usecase

import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogDetailsModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository

class ShowDetailsBattleLogUseCase(
    private val repository: BattleLogRepository
){
    suspend operator fun invoke(id: Long): BattleLogResource<BattleLogDetailsModel>{

        // some domain logic
        if(id < 0) {
            return BattleLogResource(
            data = BattleLogDetailsModel.EMPTY,
            source = BattleLogDataSourceModel.LOCAL,
            error = "id must be positive")
        }

        return repository.getBattleLogDetails(id)
    }
}
