package com.pw_manager.myapplicationpw_manager_fe.restapi

import com.pw_manager.myapplicationpw_manager_fe.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient{

    private const val serverIp = BuildConfig.SERVER_IP
    private const val BASE_URL = "http://${serverIp}:8080"

    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpInterceptor()).build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}