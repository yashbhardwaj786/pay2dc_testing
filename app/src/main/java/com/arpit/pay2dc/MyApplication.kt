package com.arpit.pay2dc

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    companion object {
        private lateinit var application: MyApplication

        @JvmStatic
        fun getInstance(): MyApplication {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    operator fun get(context: Context): MyApplication {
        return context.applicationContext as MyApplication
    }
}