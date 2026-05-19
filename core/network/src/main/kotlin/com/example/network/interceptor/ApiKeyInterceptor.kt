package com.example.network.interceptor

import com.example.buildconfig.api.BuildConfigProvider
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val buildConfigProvider: BuildConfigProvider): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${buildConfigProvider.getBrawlStarsApiKey()}")
            .build()

        return chain.proceed(request)
    }
}