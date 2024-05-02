package com.pw_manager.myapplicationpw_manager_fe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityDeleteSiteBinding
import com.pw_manager.myapplicationpw_manager_fe.databinding.ActivityMainBinding

class DeleteSiteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeleteSiteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeleteSiteBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_delete_site)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.DeleteSitebnt.setOnClickListener {
            goToMainActivity()
        }

    }

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }
}