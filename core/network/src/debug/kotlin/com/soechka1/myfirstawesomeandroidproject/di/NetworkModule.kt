package com.soechka1.myfirstawesomeandroidproject.di

import com.example.buildconfig.api.BuildConfigProvider
import com.example.network.BrawlStarsApi
import com.example.network.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.android.uprising26.network.getUnsafeOkHttpClientBuilder

val networkModule = module {

    singleOf(::ApiKeyInterceptor)

    single<OkHttpClient> {
        getUnsafeOkHttpClientBuilder()
            .addInterceptor(get<ApiKeyInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<BuildConfigProvider>().getBrawlStarsApiBaseUrl())
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<BrawlStarsApi> { get<Retrofit>().create(BrawlStarsApi::class.java) }
}