package com.example.impl

import com.example.api.BuildConfigProvider

class BuildConfigProviderImpl: BuildConfigProvider {

    override fun getBrawlStarsApiBaseUrl(): String = BuildConfig.BRAWL_STARS_BASE_URL

    override fun getBrawlerCdnBaseUrl(): String = BuildConfig.BRAWLER_CDN_BASE_URL

    override fun getBrawlStarsApiKey(): String = BuildConfig.BRAWL_STARS_API_KEY
}