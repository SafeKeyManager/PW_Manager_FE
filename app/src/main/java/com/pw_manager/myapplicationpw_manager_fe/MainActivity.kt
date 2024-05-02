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
import androidx.recyclerview.widget.RecyclerView
import com.pw_manager.myapplicationpw_manager_fe.BuildConfig
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SiteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.sitesRecyclerView)
        adapter = SiteAdapter(emptyList())
        recyclerView.adapter = adapter
        loadData()

        // ViewModel 초기화
        viewModel = ViewModelProvider(this, MyViewModelFactory(MyRepository(applicationContext))).get(MyViewModel::class.java)

        /** PostNotification 대응 */
        checkAppPushNotification()

        binding.ToAddSite.setOnClickListener {
            goToAddSiteActivity()
        }

        binding.ToDeleteSite.setOnClickListener {
            goToDeleteSiteActivity()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        recyclerView = findViewById(R.id.sitesRecyclerView)
        adapter = SiteAdapter(emptyList())
        recyclerView.adapter = adapter
        loadData()
    }

    private fun loadData(){
        RetrofitClient.apiService.getMySiteList("", 0, 10).enqueue(object : Callback<SiteResponse> {
            override fun onResponse(call: Call<SiteResponse>, response: Response<SiteResponse>) {
                if (response.isSuccessful) {
                    val siteResponse = response.body() ?: SiteResponse()
                    // 성공적으로 데이터를 받아 처리하는 로직
                    Log.d("Retrofit 응답 받기 일단 성공","Retrofit성공?")
                    Log.d("sites의 마지막 요소 : ", siteResponse.content[siteResponse.content.size-1].siteName)


                    adapter = SiteAdapter(siteResponse.content.filter { site -> site.siteStatus.equals("REGIS") })
                    recyclerView.adapter = adapter
                } else {
                    // 서버 에러 처리
                    Log.d("Retrofit 응답 받기 실패","Retrofit실패?")
                }
            }

            override fun onFailure(call: Call<SiteResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("Retrofit 통신 실패","Retrofit 실패")
                Log.e("API Error", "Request failed: ${t.message}", t)
            }
        })
    }

    private fun goToAddSiteActivity() {
        val AddSiteActivityIntent = Intent(this, AddSiteActivity::class.java)
        startActivity(AddSiteActivityIntent)
        //다시 siteLIst가져와야 하기 때문에 새로 시작하는게 ...
        finish()

    }

    private fun goToDeleteSiteActivity() {
        val DeleteSiteActivityIntent = Intent(this, DeleteSiteActivity::class.java)
        startActivity(DeleteSiteActivityIntent)
        //다시 siteLIst가져와야 하기 때문에 새로 시작하는게 ...
        finish()

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


}