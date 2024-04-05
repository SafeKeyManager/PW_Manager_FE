package com.pw_manager.myapplicationpw_manager_fe

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MyViewModel(private val repository: MyRepository) : ViewModel() {
    fun sendData(jsonData: String) {
        viewModelScope.launch {
            repository.sendPostRequest(jsonData)
            // 여기서 데이터 전송 결과를 처리
            Log.d("sendPostRequest", "성공?")
        }
    }

    fun sendFCM(jsonData: String) {
        viewModelScope.launch {
            repository.sendFcmToken(jsonData)
            // 여기서 데이터 전송 결과를 처리
            Log.d("sendFcmToken", "성공?")
        }
    }

    fun sendFirebaseToken() {
        viewModelScope.launch {
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                Log.d("inOpenNaverLogin", token)
                //token 서버로 전송
                sendFCM(token)

            } catch (e: Exception) {
                Log.e("inOpenNaverLogin", "Failed to get FCM token", e)
            }
        }
    }
}