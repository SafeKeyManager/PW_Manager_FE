package com.pw_manager.myapplicationpw_manager_fe

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient{

    private const val serverIp = BuildConfig.SERVER_IP
    private const val BASE_URL = "http://${serverIp}:8080"


    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}