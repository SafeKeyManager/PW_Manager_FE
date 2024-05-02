package com.pw_manager.myapplicationpw_manager_fe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityDeleteSiteBinding
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteSiteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeleteSiteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeleteSiteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeleteSiteBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_delete_site)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.deletesitesRecyclerView)
        adapter = DeleteSiteAdapter(emptyList())
        recyclerView.adapter = adapter
        loadData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.DeleteSitebnt.setOnClickListener {
            val checkedSites = adapter.getCheckedSiteIds()
            for (siteId in checkedSites) {
                deleteSite(siteId)
            }
            goToMainActivity()
        }

    }

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }

    private fun loadData(){
        RetrofitClient.apiService.getMySiteList("", 0, 10).enqueue(object : Callback<SiteResponse> {
            override fun onResponse(call: Call<SiteResponse>, response: Response<SiteResponse>) {
                if (response.isSuccessful) {
                    val siteResponse = response.body() ?: SiteResponse()
                    // 성공적으로 데이터를 받아 처리하는 로직
                    Log.d("Retrofit 응답 받기 일단 성공","Retrofit성공?")
                    Log.d("sites의 마지막 요소 : ", siteResponse.content[siteResponse.content.size-1].siteName)


                    adapter = DeleteSiteAdapter(siteResponse.content)
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

    private fun deleteSite(siteId : Long){
        RetrofitClient.apiService.removeSite(siteId).enqueue(object : Callback<DeleteSiteResponse> {
            override fun onResponse(call: Call<DeleteSiteResponse>, response: Response<DeleteSiteResponse>) {
                if (response.isSuccessful) {
                    val deleteSiteResponse = response.body() ?: DeleteSiteResponse(0)
                    // 성공적으로 데이터를 받아 처리하는 로직
                    Log.d("Retrofit 응답 받기 일단 성공","Retrofit성공?")
                    Log.d("제거된 id : ", "${deleteSiteResponse.id}")

                } else {
                    // 서버 에러 처리
                    Log.d("Retrofit 응답 받기 실패","Retrofit실패?")
                }
            }

            override fun onFailure(call: Call<DeleteSiteResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("Retrofit 통신 실패","Retrofit 실패")
                Log.e("API Error", "Request failed: ${t.message}", t)
            }
        })
    }
}