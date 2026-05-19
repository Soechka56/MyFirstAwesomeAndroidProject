package com.soechka1.myfirstawesomeandroidproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import com.soechka1.com.soechka1.myfirstawesomeandroidproject.di.envModule
import com.example.di.appModule
import com.soechka1.myfirstawesomeandroidproject.di.networkModule
import com.example.impl.di.searchModule
import com.example.impl.di.getBattleLogModule
import com.example.analytics.impl.di.analyticsModule
import com.soechka1.myfirstawesomeandroidproject.notifications.AppMessagingService

class MyFirstAwesomeAndroidProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        AppMessagingService.createNotificationChannels(this)

        startKoin {
            androidContext(this@MyFirstAwesomeAndroidProjectApplication)
            modules(
                appModule,
                envModule,
                networkModule,
                searchModule,
                getBattleLogModule,
                analyticsModule,
            )
        }
    }
}
