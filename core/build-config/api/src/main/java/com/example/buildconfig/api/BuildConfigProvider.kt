package com.example.buildconfig.api

interface BuildConfigProvider {

    fun isDebug(): Boolean
    fun getBrawlStarsApiBaseUrl(): String
    fun getBrawlerCdnBaseUrl(): String
    fun getBrawlStarsApiKey(): String

}
