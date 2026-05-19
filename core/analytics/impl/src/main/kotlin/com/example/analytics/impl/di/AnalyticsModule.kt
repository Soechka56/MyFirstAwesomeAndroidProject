package com.example.analytics.impl.di

import com.example.analytics.api.Analytics
import com.example.analytics.impl.AnalyticsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val analyticsModule = module {
    single<Analytics> {
        AnalyticsImpl(
            context = androidContext(),
            buildConfigProvider = get(),
        )
    }
}
