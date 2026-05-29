package com.example.test

import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource
import com.example.domain.usecase.SearchBattlesLogByHashtagQueryUseCase
import com.example.impl.SearchViewModel
import com.example.impl.model.SearchScreenEvent
import com.soechka1.myfirstawesomeandroidproject.feature.search.impl.R
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val searchUseCase: SearchBattlesLogByHashtagQueryUseCase = mockk()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(searchUseCase)
    }

    @After
    fun close() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search success updates state to loading then to success and emits event`() = runTest(testDispatcher) {
        val hashtag = "test"

        val expectedBattles = listOf(
            BattleLogItemModel(
                id = 1L,
                battleTime = "2026-05-29T12:00:00Z",
                mode = "Brawl Ball",
                result = "victory",
                trophyChange = 8
            )
        )

        val mockResource = mockk<BattleLogResource<List<BattleLogItemModel>>> {
            coEvery { data } returns expectedBattles
            coEvery { source } returns BattleLogDataSourceModel.SERVER
        }

        coEvery { searchUseCase(hashtag) } coAnswers {
            delay(1)
            mockResource
        }

        val eventResults = mutableListOf<SearchScreenEvent>()
        val eventJob = launch { viewModel.events.collect { eventResults.add(it) } }

        viewModel.updateHashtag("#$hashtag")
        viewModel.search()

        runCurrent()
        assertTrue(viewModel.state.value.isLoading)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(expectedBattles, viewModel.state.value.battles)
        assertEquals(null, viewModel.state.value.errorMessage)

        assertEquals(1, eventResults.size)

        val expectedEvent = SearchScreenEvent.ShowSourceMessage(
            R.string.battle_log_network
        )
        assertEquals(expectedEvent, eventResults.first())

        eventJob.cancel()
    }

    @Test
    fun `search failure updates state to loading then to error`() = runTest(testDispatcher) {
        val hashtag = "test"
        val exceptionMessage = "Network Error"

        coEvery { searchUseCase(hashtag) } coAnswers {
            delay(1)
            throw RuntimeException(exceptionMessage)
        }

        viewModel.updateHashtag(hashtag)
        viewModel.search()

        runCurrent()
        assertTrue(viewModel.state.value.isLoading)
        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.battles.isEmpty())
        assertEquals(exceptionMessage, viewModel.state.value.errorMessage)
    }
}