package com.owasp.top.mobile.demo.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.owasp.top.mobile.demo.R
import com.owasp.top.mobile.demo.databinding.ActivityMainBinding
import com.owasp.top.mobile.demo.environment.FlavorApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()

    }

    private fun setupUI(){
        if (FlavorApp.isSecure()){
            binding.title.setText(R.string.title_login_segura)
        } else {
            binding.title.setText(R.string.title_login_insegura)
        }

        binding.acceder.setOnClickListener {

        }

    }
}