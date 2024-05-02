package com.pw_manager.myapplicationpw_manager_fe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityAddSiteBinding
import org.json.JSONException
import org.json.JSONObject

class AddSiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSiteBinding
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MyViewModelFactory(MyRepository(applicationContext))).get(MyViewModel::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.SiteAddSubmitBnt.setOnClickListener {
            addSite()
            goToMainActivity()
        }
    }

    private fun addSite(){
        val siteName = binding.AddSiteName.text.toString()
        val siteURL = binding.AddSiteURL.text.toString()
        val updateCycle = binding.AddSiteUpdateCycle.text.toString()

        val jsonObject = JSONObject()
        try {
            jsonObject.put("siteName", siteName)
            jsonObject.put("siteUrl",siteURL)
            jsonObject.put("siteCycle",updateCycle)
        }catch (e: JSONException) {
            e.printStackTrace()  // JSON 오류 처리
        }
        Log.d("jsonData 잘 만들어졌나?", jsonObject.toString())
        viewModel.sendAddSite(jsonObject.toString())
    }

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }
}