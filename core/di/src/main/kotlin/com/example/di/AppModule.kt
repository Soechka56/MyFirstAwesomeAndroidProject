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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

import com.example.di.AppConstants.DATABASE_NAME
import com.example.di.AppConstants.DISPATCHER_IO
object AppConstants {
    const val DISPATCHER_IO = "DISPATCHER_IO"
    const val DATABASE_NAME = "battle_log_database"
}

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            BattleLogDatabase::class.java,
            DATABASE_NAME
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
            dispatcher = get(named(DISPATCHER_IO)),
        )
    }

    // use cases
    factoryOf(::SearchBattlesLogByHashtagQueryUseCase)
    factoryOf(::ShowDetailsBattleLogUseCase)

    // Some Utils
    single<CoroutineDispatcher>(named(DISPATCHER_IO)) { Dispatchers.IO }
}