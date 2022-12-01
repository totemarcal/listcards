package com.example.listcards

import android.app.Application
import com.example.listcards.helper.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ListCardApplication : Application() {

    init {
        appInstance = this
    }

    companion object {
        private var appInstance: ListCardApplication? = null
        fun getInstance(): ListCardApplication {
            return appInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@ListCardApplication)
            modules(mainModule)
        }
    }

}