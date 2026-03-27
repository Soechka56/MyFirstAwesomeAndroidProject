package com.example.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.di.ServiceLocator
import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.usecase.SearchBattlesLogByHashtagQueryUseCase
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class SearchViewModel(
    private val searchBattlesLogByHashtagQueryUseCase: SearchBattlesLogByHashtagQueryUseCase,
) : ViewModel() {
    var hashtag by mutableStateOf("")
        private set

    var battles by mutableStateOf<List<BattleLogItemModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var sourceEvent by mutableStateOf<Pair<Long, String>?>(null)
        private set

    fun updateHashtag(value: String) {
        hashtag = value
    }

    fun search() {
        val currentHashtag = hashtag.trim().removePrefix("#")
        if (currentHashtag.isEmpty()) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            battles = emptyList()
            runCatching {
                searchBattlesLogByHashtagQueryUseCase(currentHashtag)
            }.onSuccess { resource ->
                battles = resource.data
                sourceEvent = System.currentTimeMillis() to resource.source.toMessage()
            }.onFailure {
                errorMessage = it.message
            }
            isLoading = false
        }
    }

    fun clearSourceEvent() {
        sourceEvent = null
    }

    private fun BattleLogDataSourceModel.toMessage(): String {
        return when (this) {
            BattleLogDataSourceModel.SERVER -> "Data source: server"
            BattleLogDataSourceModel.LOCAL -> "Data source: local"
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: KClass<T>,
                extras: CreationExtras
            ): T {
                val useCase = ServiceLocator.getSearchBattlesLogByHashtagQueryUseCase()

                return SearchViewModel(
                    searchBattlesLogByHashtagQueryUseCase = useCase,
                ) as T
            }
        }
    }
}
