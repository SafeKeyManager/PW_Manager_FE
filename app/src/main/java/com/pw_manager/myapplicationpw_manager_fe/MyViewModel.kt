package com.pw_manager.myapplicationpw_manager_fe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel(private val repository: MyRepository) : ViewModel() {
    fun sendData(jsonData: String) {
        viewModelScope.launch {
            repository.sendPostRequest(jsonData)
            // 여기서 데이터 전송 결과를 처리
            Log.d("sendPostRequest", "성공?")
        }
    }
}