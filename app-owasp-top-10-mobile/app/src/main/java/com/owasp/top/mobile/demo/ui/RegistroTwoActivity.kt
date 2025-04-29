package com.owasp.top.mobile.demo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.owasp.top.mobile.demo.R
import com.owasp.top.mobile.demo.databinding.ActivityRegistroTwoBinding
import com.owasp.top.mobile.demo.domain.MainViewModel
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.ui.common.AlertDialogApp
import com.owasp.top.mobile.demo.ui.common.DialogLoader
import com.owasp.top.mobile.demo.utils.HelperPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroTwoBinding
    private val viewModel: MainViewModel by viewModels()
    private var dialogLoader: DialogLoader? = null
    private var passwordValid: Boolean = false
    private var helperPassword: HelperPassword? = null
    private var extras: Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extras = intent.extras

        dialogLoader = DialogLoader(this)
        helperPassword = HelperPassword(this)

        setupUI()

        observables()

    }

    private fun setupUI(){
        if (FlavorApp.isSecure()){
            binding.toolbar.title = getString(R.string.title_login_segura)
        } else {
            binding.toolbar.title = getString(R.string.title_login_insegura)
        }

        passwordValidation()

        binding.register.setOnClickListener {
            if (passwordValid){
                signUp()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun passwordValidation(){
        if (FlavorApp.isInsecure()) {
            binding.optionsPassword.visibility = View.GONE
        }

        binding.confPassword.editText?.doAfterTextChanged {
            validatePassword(it.toString())
        }

        binding.password.editText?.doAfterTextChanged {
            validatePassword(it.toString())
        }
    }

    private fun validatePassword(it: String){
        passwordValid = true
        if (FlavorApp.isSecure()){
            with(helperPassword?.validatorMinSize(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyMinSizeLabel,
                    binding.policyMinSizeCheck,
                    this
                )
            }

            with(helperPassword?.validatorMaxSize(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyMaxSizeLabel,
                    binding.policyMaxSizeCheck,
                    this
                )
            }

            with(helperPassword?.validatorHasUppercase(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyHasUppercaseLabel,
                    binding.policyHasUppercaseCheck,
                    this
                )
            }

            with(helperPassword?.validatorHasLowercase(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyHasLowercaseLabel,
                    binding.policyHasLowercaseCheck,
                    this
                )
            }

            with(helperPassword?.validatorHasDigit(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyHasDigitsLabel,
                    binding.policyHasDigitsCheck,
                    this
                )
            }

            with(helperPassword?.validatorHasSymbol(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyHasSymbolLabel,
                    binding.policyHasSymbolCheck,
                    this
                )
            }

            with(helperPassword?.validatorNotSpace(it)?.isValid ?: false){
                passwordValid = passwordValid && this
                changeStatus(
                    binding.policyNotSpaceLabel,
                    binding.policyNotSpaceCheck,
                    this
                )
            }


        }

        with(helperPassword?.validatorPassConf(
            binding.password.editText?.text.toString(),
            binding.confPassword.editText?.text.toString()
        )?.isValid ?: false){
            passwordValid = passwordValid && this
            changeStatus(
                binding.policyPasswordEqualsLabel,
                binding.policyPasswordEqualsCheck,
                this
            )
        }

        binding.register.isEnabled = passwordValid
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun changeStatus(textView: TextView, imageView: ImageView, result: Boolean){
        if (result){
            imageView.setImageResource(android.R.drawable.checkbox_on_background)
            textView.setTextColor(Color.parseColor(getString(android.R.color.holo_green_light)))
        } else {
            imageView.setImageResource(android.R.drawable.checkbox_off_background)
            textView.setTextColor(Color.parseColor(getString(android.R.color.darker_gray)))

        }
    }


    private fun signUp(){
        viewModel.signUp(
            username = extras?.getString("username") ?: "",
            password = binding.password.editText?.text.toString(),
            name = extras?.getString("name") ?: "",
            last_name = extras?.getString("last_name") ?: "",
            phone = extras?.getString("phone") ?: ""
        )
    }

    private fun observables(){
        viewModel.errorObservale().observe(this){
            AlertDialogApp(this@RegistroTwoActivity)
                .apply {
                    setTitle("Aviso")
                    setMessage("Ocurrio un error. \nDetalles: \n$it")
                    setPositiveButton("De acuerdo"){
                    }

                }.show()
        }

        viewModel.isLoading().observe(this){
            dialogLoader?.isLoading(it)
        }

        viewModel.isSuccessSignUp().observe(this) {
            if (it){
                AlertDialogApp(this@RegistroTwoActivity)
                    .apply {
                        setTitle("Completado")
                        setMessage("Se registro correctamente, puede proceder a Inicio de sesión")
                        setPositiveButton("De acuerdo"){
                            startActivity(
                                Intent(this@RegistroTwoActivity, MainActivity::class.java)
                                    .apply {
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                                    }
                            )
                            finish()
                        }

                    }.show()
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}