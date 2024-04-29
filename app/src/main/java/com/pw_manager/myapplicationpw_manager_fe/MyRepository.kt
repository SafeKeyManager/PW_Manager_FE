package com.pw_manager.myapplicationpw_manager_fe

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class MyRepository(
    private val context: Context
) {

    private val client = OkHttpClient()
    private val serverIp = BuildConfig.SERVER_IP
    suspend fun sendPostRequest(jsonData:String) = withContext(Dispatchers.IO){
        //if(jwtToken == null)
        val jwtToken = getJwtToken()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonData.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://${serverIp}:8080/site/add")
            .addHeader("Authorization","Bearer ${jwtToken}")
            .post(requestBody)
            .build()
        Log.d("실제 보내는 jwt토큰 형식","Bearer ${jwtToken}")
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                // 요청 실패 처리
                Log.d("http://schedules.store/site/add", "요청 실패")
            } else {
                Log.d("http://schedules.store/site/add", "요청 성공")
            }
        }
    }

    private fun getJwtToken(): String? {
        val sharedPreferences = context.getSharedPreferences("JwtToken", Context.MODE_PRIVATE)
        return sharedPreferences.getString("JwtToken", null)
    }

    suspend fun sendFcmToken(jsonData:String) = withContext(Dispatchers.IO){
        val jwtToken = getJwtToken()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonData.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://${serverIp}:8080/api/v1/fcm/token")
            .addHeader("Authorization","Bearer ${jwtToken}")
            .post(requestBody)
            .build()
        Log.d("실제 보내는 fcm토큰 형식", jsonData)
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                // 요청 실패 처리
                Log.d("http://schedules.store/api/v1/fcm/token", "요청 실패")
            } else {
                Log.d("http://${serverIp}:8080/api/v1/fcm/token", "요청 성공")
            }
        }
    }


}