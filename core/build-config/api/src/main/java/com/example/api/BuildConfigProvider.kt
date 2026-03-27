package com.example.api

interface BuildConfigProvider {

    fun getBrawlStarsApiBaseUrl(): String
    fun getBrawlerCdnBaseUrl(): String
    fun getBrawlStarsApiKey(): String

}
