package com.example.impl

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.usecase.SearchBattlesLogByHashtagQueryUseCase
import com.example.impl.model.SearchScreenEvent
import com.example.impl.model.SearchScreenState
import com.soechka1.myfirstawesomeandroidproject.feature.search.impl.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchBattlesLogByHashtagQueryUseCase: SearchBattlesLogByHashtagQueryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<SearchScreenEvent>()
    val events = _events.asSharedFlow()

    fun updateHashtag(value: String) {
        _state.update { it.copy(hashtag = value) }
    }

    fun search() {
        val currentHashtag = _state.value.hashtag.trim().removePrefix("#")
        if (currentHashtag.isEmpty()) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    battles = emptyList()
                )
            }
            runCatching {
                searchBattlesLogByHashtagQueryUseCase(currentHashtag)
            }.onSuccess { resource ->
                val countByMode = resource.data
                    .mapNotNull { it.mode }
                    .groupingBy { it }
                    .eachCount()

                _state.update {
                    it.copy(
                        battles = resource.data,
                        battlesCountByMode = countByMode,
                        isLoading = false
                    )
                }
                _events.emit(
                    SearchScreenEvent.ShowSourceMessage(
                        when (resource.source) {
                            BattleLogDataSourceModel.SERVER -> R.string.battle_log_network
                            BattleLogDataSourceModel.LOCAL -> R.string.battle_log_local
                        }
                    )
                )
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
