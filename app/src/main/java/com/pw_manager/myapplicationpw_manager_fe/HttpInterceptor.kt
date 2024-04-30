package com.pw_manager.myapplicationpw_manager_fe

import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req =
            chain.request().newBuilder().addHeader("Authorization", "Bearer ${App.prefs.token}").build()
        return chain.proceed(req)
    }
}