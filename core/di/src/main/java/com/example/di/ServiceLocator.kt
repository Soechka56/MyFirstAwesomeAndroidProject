package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.api.BuildConfigProvider
import com.example.data.mapper.BattleLogMapper
import com.example.data.model.BattleLogDatabase
import com.example.data.repository.BattleLogRepositoryImpl
import com.example.domain.usecase.SearchBattlesLogByHashtagQueryUseCase
import com.example.domain.usecase.ShowDetailsBattleLogUseCase
import com.example.impl.BuildConfigProviderImpl
import com.example.network.BrawlStarsApi
import com.example.network.interceptors.ApiKeyInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.android.uprising26.network.getUnsafeOkHttpClientBuilder

object ServiceLocator {
    private lateinit var buildConfigProvider: BuildConfigProvider

    private lateinit var searchBattlesLogByHashtagQueryUseCase: SearchBattlesLogByHashtagQueryUseCase

    private lateinit var showDetailsBattleLogUseCase: ShowDetailsBattleLogUseCase

    fun init(context: Context) {
        buildConfigProvider = BuildConfigProviderImpl()

        val api = Retrofit.Builder()
            .baseUrl(buildConfigProvider.getBrawlStarsApiBaseUrl())
            .client(
                getUnsafeOkHttpClientBuilder()
                    .addInterceptor(ApiKeyInterceptor(buildConfigProvider))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BrawlStarsApi::class.java)

        val database = Room.databaseBuilder(
            context.applicationContext,
            BattleLogDatabase::class.java,
            "battle_log.db",
        ).build()

        val repository = BattleLogRepositoryImpl(
            brawlStarsApi = api,
            battleLogDao = database.battleLogDao(),
            battleLogMapper = BattleLogMapper(),
        )

        searchBattlesLogByHashtagQueryUseCase = SearchBattlesLogByHashtagQueryUseCase(repository)
        showDetailsBattleLogUseCase = ShowDetailsBattleLogUseCase(repository)
    }

    fun getBuildConfigProvider(): BuildConfigProvider = buildConfigProvider

    fun getSearchBattlesLogByHashtagQueryUseCase(): SearchBattlesLogByHashtagQueryUseCase {
        return searchBattlesLogByHashtagQueryUseCase
    }

    fun getShowDetailsBattleLogUseCase(): ShowDetailsBattleLogUseCase {
        return showDetailsBattleLogUseCase
    }
}
