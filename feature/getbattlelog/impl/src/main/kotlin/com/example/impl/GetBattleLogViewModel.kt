package com.example.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.usecase.ShowDetailsBattleLogUseCase
import com.example.impl.model.GetBattleLogScreenEvent
import com.example.impl.model.GetBattleLogScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.soechka1.myfirstawesomeandroidproject.feature.getbattlelog.impl.R

class GetBattleLogViewModel(
    private val battleId: Long,
    private val showDetailsBattleLogUseCase: ShowDetailsBattleLogUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GetBattleLogScreenState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<GetBattleLogScreenEvent>()
    val events = _events.asSharedFlow()

    init {
        loadBattleDetails()
    }

    fun loadBattleDetails() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            runCatching {
                showDetailsBattleLogUseCase(battleId)
            }.onSuccess { resource ->
                _state.update {
                    it.copy(
                        battleDetails = resource.data,
                        isLoading = false
                    )
                }
                val resId = when (resource.source) {
                    BattleLogDataSourceModel.SERVER -> R.string.battle_log_network
                    BattleLogDataSourceModel.LOCAL -> R.string.battle_log_local
                }
                _events.emit(GetBattleLogScreenEvent.ShowSourceMessageRes(resId))
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        errorMessage = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}
