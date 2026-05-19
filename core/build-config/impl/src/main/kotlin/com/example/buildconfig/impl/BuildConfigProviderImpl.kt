package com.example.buildconfig.impl

import com.example.buildconfig.api.BuildConfigProvider
import com.example.buildconfig.impl.BuildConfig


class BuildConfigProviderImpl: BuildConfigProvider {

    override fun isDebug(): Boolean = BuildConfig.DEBUG

    override fun getBrawlStarsApiBaseUrl(): String = BuildConfig.BRAWL_STARS_BASE_URL

    override fun getBrawlerCdnBaseUrl(): String = BuildConfig.BRAWLER_CDN_BASE_URL

    override fun getBrawlStarsApiKey(): String = BuildConfig.BRAWL_STARS_API_KEY
}