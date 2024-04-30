package com.pw_manager.myapplicationpw_manager_fe

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm="mPref"
    private val prefs=context.getSharedPreferences(prefNm,MODE_PRIVATE)

    var token:String?
        get() = prefs.getString("JwtToken",null)
        set(value){
            prefs.edit().putString("JwtToken",value).apply()
        }
}