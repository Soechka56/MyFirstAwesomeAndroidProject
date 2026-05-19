package com.example.di

import androidx.room.Room
import com.example.data.mapper.BattleLogMapper
import com.example.data.model.BattleLogDatabase
import com.example.data.repository.BattleLogRepositoryImpl
import com.example.domain.repository.BattleLogRepository
import com.example.domain.usecase.SearchBattlesLogByHashtagQueryUseCase
import com.example.domain.usecase.ShowDetailsBattleLogUseCase
import com.example.network.error.GeneralExceptionHandler
import com.example.network.error.impl.BrawlStarsExceptionHandlerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            BattleLogDatabase::class.java,
            "battle_log_database"
        ).build()
    }

    single { get<BattleLogDatabase>().battleLogDao() }

    // Mapper
    singleOf(::BattleLogMapper)

    // Exception Handler
    single<GeneralExceptionHandler> { BrawlStarsExceptionHandlerImpl() }

    // Repository
    single<BattleLogRepository> {
        BattleLogRepositoryImpl(
            brawlStarsApi = get(),
            battleLogDao = get(),
            battleLogMapper = get(),
            generalExceptionHandler = get(),
        )
    }

    // use cases
    factoryOf(::SearchBattlesLogByHashtagQueryUseCase)
    factoryOf(::ShowDetailsBattleLogUseCase)
}