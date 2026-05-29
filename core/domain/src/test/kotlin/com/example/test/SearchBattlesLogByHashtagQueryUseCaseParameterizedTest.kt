package com.example.test

import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository
import com.example.domain.usecase.SearchBattlesLogByHashtagQueryUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class SearchBattlesLogByHashtagQueryUseCaseParameterizedTest(
    private val hashtagQuery: String,
    private val expectedError: Boolean,
    private val shouldCallRepo: Boolean
) {

    // private val repo: BattleLogRepository = mockk()
    private val repo = mockk<BattleLogRepository>()
    private val outputDataSize = 10
    private val useCase = SearchBattlesLogByHashtagQueryUseCase(repo)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} - запрос, ожидаем вызов репо: {2}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("", true, false),
                arrayOf("hello, world!", true, false),
                arrayOf("R80LYJGC", false, true)
            )
        }
    }

    @Test
    fun `verify search query validation`() = runTest {
        if (shouldCallRepo) {
            coEvery { repo.searchByUserHashtag(hashtagQuery) } returns BattleLogResource(
                List(outputDataSize) {
                    mockk<BattleLogItemModel>(
                        relaxed = true
                    )
                }, source = BattleLogDataSourceModel.LOCAL
            )
        }

        val result = useCase(hashtagQuery)

        if(expectedError){
            assert(result.error.isNotEmpty())
            coVerify(exactly = 0) { repo.searchByUserHashtag(any()) }
        } else {
            assertEquals(outputDataSize, result.data.size)
            coVerify(exactly = 1) { repo.searchByUserHashtag(hashtagQuery) }
        }
    }
}