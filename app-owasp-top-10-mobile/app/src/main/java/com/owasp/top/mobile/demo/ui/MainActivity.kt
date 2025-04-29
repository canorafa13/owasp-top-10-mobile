package com.owasp.top.mobile.demo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.owasp.top.mobile.demo.R
import com.owasp.top.mobile.demo.databinding.ActivityMainBinding
import com.owasp.top.mobile.demo.domain.MainViewModel
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.ui.common.DialogLoader
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var dialogLoader: DialogLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogLoader = DialogLoader(this)

        setupUI()

        observables()

    }

    private fun setupUI(){
        if (FlavorApp.isSecure()){
            binding.title.setText(R.string.title_login_segura)
        } else {
            binding.title.setText(R.string.title_login_insegura)
        }

        binding.acceder.setOnClickListener {
            viewModel.login(
                username = binding.user.editText?.text.toString(),
                password = binding.password.editText?.text.toString(),
                remember = binding.rememberMe.isChecked
            )
        }

    }

    private fun observables(){
        viewModel.errorObservale().observe(this){
            showToast(it)
        }

        viewModel.isLoading().observe(this){
            dialogLoader?.isLoading(it)
        }

        viewModel.usernameObservable().observe(this){
            binding.user.editText?.setText(it)
        }

        viewModel.passwordObservable().observe(this){
            binding.password.editText?.setText(it)
        }

        viewModel.responseObservable().observe(this){ response ->
            response?.let {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            } ?: run { showToast("Ocurrio un error inesperado") }
        }

        viewModel.getCredentials()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}