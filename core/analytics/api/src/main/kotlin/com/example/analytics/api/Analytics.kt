package com.example.analytics.api

interface Analytics {
    fun trackScreenView(screenName: String, screenClass: String)
    fun trackEvent(eventName: String, vararg params: Pair<String, Any>)
}
