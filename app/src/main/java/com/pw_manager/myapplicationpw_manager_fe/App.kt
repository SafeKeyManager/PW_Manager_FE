package com.pw_manager.myapplicationpw_manager_fe

import android.app.Application

class App : Application() {
    companion object{
        lateinit var prefs:Prefs
    }
    override fun onCreate() {
        prefs=Prefs(applicationContext)
        super.onCreate()
    }
}