package com.soechka1.com.soechka1.myfirstawesomeandroidproject.di

import com.example.buildconfig.api.BuildConfigProvider
import com.example.buildconfig.impl.BuildConfigProviderImpl

import org.koin.dsl.module

val envModule = module {
    single<BuildConfigProvider>{ BuildConfigProviderImpl() }
}