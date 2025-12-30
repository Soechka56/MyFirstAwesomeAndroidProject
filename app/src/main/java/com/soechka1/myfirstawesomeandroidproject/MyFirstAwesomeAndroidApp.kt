package com.soechka1.myfirstawesomeandroidproject

import android.app.Application
import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.repo.UserDataRepository

class MyFirstAwesomeAndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initDatabase(this)
        UserDataRepository.init(this)
    }
}