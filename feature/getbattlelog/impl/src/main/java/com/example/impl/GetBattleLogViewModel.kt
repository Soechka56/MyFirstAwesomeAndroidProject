package com.example.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.di.ServiceLocator
import com.example.domain.model.BattleLogDetailsModel
import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.usecase.ShowDetailsBattleLogUseCase
import kotlinx.coroutines.launch
import kotlin.reflect.KClass
import com.soechka1.myfirstawesomeandroidproject.feature.getbattlelog.impl.R

class GetBattleLogViewModel(
    private val battleId: Long,
    private val showDetailsBattleLogUseCase: ShowDetailsBattleLogUseCase,
) : ViewModel() {

    var battleDetails by mutableStateOf<BattleLogDetailsModel?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var sourceEvent by mutableStateOf<Pair<Long, Int>?>(null)
        private set

    init {
        loadBattleDetails()
    }

    fun loadBattleDetails() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            runCatching {
                showDetailsBattleLogUseCase(battleId)
            }.onSuccess { resource ->
                battleDetails = resource.data
                sourceEvent = System.currentTimeMillis() to when (resource.source) {
                    BattleLogDataSourceModel.SERVER -> R.string.battle_log_network
                    BattleLogDataSourceModel.LOCAL -> R.string.battle_log_local
                }
            }.onFailure {
                errorMessage = it.message
            }
            isLoading = false
        }
    }

    fun clearSourceEvent() {
        sourceEvent = null
    }


    companion object {
        val BattleIdKey = object : CreationExtras.Key<Long> {}

        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: KClass<T>,
                extras: CreationExtras
            ): T {
                val battleId = extras[BattleIdKey] ?: error("battleId not found")
                val useCase = ServiceLocator.getShowDetailsBattleLogUseCase()

                return GetBattleLogViewModel(
                    battleId = battleId,
                    showDetailsBattleLogUseCase = useCase,
                ) as T
            }
        }
    }
}
