package com.example.test

import com.example.domain.model.BattleLogDataSourceModel
import com.example.domain.model.BattleLogDetailsModel
import com.example.domain.model.BattleLogItemModel
import com.example.domain.model.BattleLogResource
import com.example.domain.repository.BattleLogRepository
import com.example.domain.usecase.ShowDetailsBattleLogUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ShowDetailsBattleLogUseCaseParameterizedTest(
    private val id: Long,
    private val expectedError: Boolean,
    private val shouldCallRepo: Boolean
) {
    // private val repo: BattleLogRepository = mockk()
    private val repo = mockk<BattleLogRepository>()
    private val expectedOutput = mockk<BattleLogDetailsModel>(relaxed = true)
    private val expectedBattleId = 67L
    private val useCase = ShowDetailsBattleLogUseCase(repo)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} - запрос, ожидаем вызов репо: {2}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(67L, false, true),
                arrayOf(-100L, true, false),
            )
        }
    }

    @Test
    fun `verify battle id validation`() = runTest {

        every { expectedOutput.id } returns id

        if (shouldCallRepo) {
            coEvery { repo.getBattleLogDetails(id) } returns BattleLogResource(
                expectedOutput, source = BattleLogDataSourceModel.LOCAL
            )
        }

        val result = useCase(id)

        if(expectedError){
            assert(result.error.isNotEmpty())
            coVerify(exactly = 0) { repo.getBattleLogDetails(any()) }
        } else {
            assertEquals(expectedBattleId, result.data.id)
            coVerify(exactly = 1) { repo.getBattleLogDetails(id) }
        }
    }
}