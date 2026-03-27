package com.soechka1.myfirstawesomeandroidproject

import android.app.Application
import com.example.di.ServiceLocator

class MyFirstAwesomeAndroidProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}
