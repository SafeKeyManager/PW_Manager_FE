package com.pw_manager.myapplicationpw_manager_fe

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pw_manager.myapplicationpw_manager_fe.MyFirebaseMessagingService
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** FCM설정, Token값 가져오기 */
        MyFirebaseMessagingService().getFirebaseToken()

        /** PostNotification 대응 */
        checkAppPushNotification()

        //사용안하면 삭제하기
        /** DynamicLink 수신확인 */
        initDynamicLink()

        // 버튼 클릭 리스너 설정
        binding.button.setOnClickListener {
            openNaverLoginPage()
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent) // 새 인텐트를 현재 인텐트로 설정

        val data = intent?.data
        if (data != null && data.scheme == "secretmanagerapp" && data.host == "oauthcallback") {
            val token = data.getQueryParameter("token")

            Log.d("JWT-Token", "jwt token: $token")
        }
    }

    // 버튼 클릭 시 호출될 함수
    private fun openNaverLoginPage() {

        val url = "http://192.168.219.104:8080/oauth2/authorization/naver"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    /** Android 13 PostNotification */
    private fun checkAppPushNotification() {
        //Android 13 이상 && 푸시권한 없음
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            && PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
            // 푸쉬 권한 없음
            permissionPostNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }
    }

    /** 권한 요청 */
    private val permissionPostNotification = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            //권한 허용
        } else {
            //권한 비허용
        }
    }




    //사용안하면 삭제하기
    /** DynamicLink */
    private fun initDynamicLink() {
        val dynamicLinkData = intent.extras
        if (dynamicLinkData != null) {
            var dataStr = "DynamicLink 수신받은 값\n"
            for (key in dynamicLinkData.keySet()) {
                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
            }

            binding.tvToken.text = dataStr
        }
    }

    fun getSavedFcmToken(): String {
        // SharedPreferences에서 FCM 토큰을 가져오는 코드
        val pref = applicationContext.getSharedPreferences("token", Context.MODE_PRIVATE)
        return pref.getString("token", "") ?: ""
    }
}