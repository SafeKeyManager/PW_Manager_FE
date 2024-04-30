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
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.pw_manager.myapplicationpw_manager_fe.BuildConfig
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel 초기화
        viewModel = ViewModelProvider(this, MyViewModelFactory(MyRepository(applicationContext))).get(MyViewModel::class.java)

        /** PostNotification 대응 */
        checkAppPushNotification()

        //사용안하면 삭제하기
        /** DynamicLink 수신확인 */
        //initDynamicLink()

        binding.ToAddSite.setOnClickListener {
            goToAddSiteActivity()
        }
    }

    private fun goToAddSiteActivity() {
        val AddSiteActivityIntent = Intent(this, AddSiteActivity::class.java)
        startActivity(AddSiteActivityIntent)
        //finish()
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
//    private fun initDynamicLink() {
//        val dynamicLinkData = intent.extras
//        if (dynamicLinkData != null) {
//            var dataStr = "DynamicLink 수신받은 값\n"
//            for (key in dynamicLinkData.keySet()) {
//                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
//            }
//
//            binding.tvToken.text = dataStr
//        }
//    }

}