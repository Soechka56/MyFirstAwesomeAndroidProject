package com.example.analytics.impl

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import com.example.analytics.api.Analytics
import com.example.buildconfig.api.BuildConfigProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class AnalyticsImpl(
    private val context: Context,
    private val buildConfigProvider: BuildConfigProvider,
) : Analytics {

    @get:SuppressLint("MissingPermission")
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    override fun trackScreenView(screenName: String, screenClass: String) {
        if (buildConfigProvider.isDebug()) {
            Log.i("Analytics", "SCREEN_VIEW: $screenName ($screenClass)")
        }
        @Suppress("DEPRECATION")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
        }
    }

    override fun trackEvent(eventName: String, vararg params: Pair<String, Any>) {
        val paramsBundle = bundleOf(*params.map { (k, v) -> k to v.toString() }.toTypedArray())
        if (buildConfigProvider.isDebug()) {
            Log.i("Analytics", "EVENT: $eventName; params=$paramsBundle")
        }
        firebaseAnalytics.logEvent(eventName, paramsBundle)
    }
}
