package com.owasp.top.mobile.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.owasp.top.mobile.demo.R
import com.owasp.top.mobile.demo.databinding.ActivityMainBinding
import com.owasp.top.mobile.demo.domain.MainViewModel
import com.owasp.top.mobile.demo.environment.FlavorApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


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

        viewModel.usernameObservable().observe(this){
            binding.user.editText?.setText(it)
        }

        viewModel.passwordObservable().observe(this){
            binding.password.editText?.setText(it)
        }

        viewModel.responseObservable().observe(this){ response ->
            response?.let {
                showToast("Logueado correctamente ${it.name} ${it.last_name}")
            } ?: run { showToast("Ocurrio un error inesperado") }
        }

        viewModel.getCredentials()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}