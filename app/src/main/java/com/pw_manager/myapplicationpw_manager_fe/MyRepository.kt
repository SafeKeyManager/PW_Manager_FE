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

    suspend fun sendPostRequest(jsonData:String) = withContext(Dispatchers.IO){
        //if(jwtToken == null)
        val jwtToken = getToken()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonData.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://192.168.219.104:8080/site/add")
            .addHeader("Authorization","Bearer ${jwtToken}")
            .post(requestBody)
            .build()
        Log.d("실제 보내는 jwt토큰 형식","Bearer ${jwtToken}")
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                // 요청 실패 처리
                Log.d("http://192.168.219.104:8080/site/add", "요청 실패")
            } else {
                Log.d("http://192.168.219.104:8080/site/add", "요청 성공")
            }
        }
    }

    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("JwtToken", Context.MODE_PRIVATE)
        return sharedPreferences.getString("JwtToken", null)
    }
}