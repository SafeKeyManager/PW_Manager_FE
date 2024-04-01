package com.pw_manager.myapplicationpw_manager_fe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
}