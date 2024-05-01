package com.pw_manager.myapplicationpw_manager_fe.activity.delete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pw_manager.myapplicationpw_manager_fe.R
import com.pw_manager.myapplicationpw_manager_fe.activity.MainActivity
import com.pw_manager.myapplicationpw_manager_fe.adapter.DeleteSiteAdapter
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityDeleteSiteBinding
import com.pw_manager.myapplicationpw_manager_fe.entity.DeleteResponse
import com.pw_manager.myapplicationpw_manager_fe.entity.Site
import com.pw_manager.myapplicationpw_manager_fe.restapi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteSiteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeleteSiteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeleteSiteAdapter
    private lateinit var sites: ArrayList<Site>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeleteSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_delete_site)

        // Intent에서 Parcelable 리스트 꺼내기
        sites = intent.getParcelableArrayListExtra<Site>("sites_redis") ?: arrayListOf()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DeleteSiteAdapter(sites)
        recyclerView.adapter = adapter

        binding.deleteSiteBnt.setOnClickListener {
            val selectedSiteIds = sites.filter { it.isChecked }.map { it.id }

            selectedSiteIds.forEach {
                if(it != null){
                    DeleteData(it)
                }
            }
            goToMainActivity()
        }
    }
    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }

    private fun DeleteData(id: Long){
        RetrofitClient.apiService.deleteSite(id).enqueue(object : Callback<DeleteResponse>{
            override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                if(response.isSuccessful){
                    val siteId = response.body() ?: DeleteResponse()
                    Log.d("삭제된 Site id : ","${siteId}")
                }
                else{
                    Log.d("delete site Retrofit 응답은 받았는데 성공못함","response isFailure")
                }
            }
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                Log.d("delete site Retrofit 응답 실패","Retrofit isFailure")
            }
        })
    }
}