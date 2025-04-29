package com.owasp.top.mobile.demo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.owasp.top.mobile.demo.R
import com.owasp.top.mobile.demo.databinding.AcitivityRegistroOneBinding
import com.owasp.top.mobile.demo.domain.MainViewModel
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.ui.common.DialogLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroOneActivity : AppCompatActivity() {
    private lateinit var binding: AcitivityRegistroOneBinding
    private val viewModel: MainViewModel by viewModels()
    private var dialogLoader: DialogLoader? = null
    private val formRegistro: FormRegistro = FormRegistro()

    data class FormRegistro(
        val user: ItemForm = ItemForm(),
        val name: ItemForm = ItemForm(),
        val lastName: ItemForm = ItemForm(),
        val phone: ItemForm = ItemForm()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AcitivityRegistroOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogLoader = DialogLoader(this)

        setupUI()

        observables()

    }

    private fun setupUI(){
        if (FlavorApp.isSecure()){
            binding.toolbar.title = getString(R.string.title_registro_segura)
        } else {
            binding.toolbar.title = getString(R.string.title_registro_insegura)
        }


        binding.user.editText?.doAfterTextChanged {
            formRegistro.user.apply {
                value = it.toString()
                isValid = if (it.toString().isEmpty()) {
                    binding.user.error = "No se permite el campo vacio"
                    false
                } else {
                    binding.user.error = null
                    true
                }
            }

            validateBtnEnabled()
        }

        binding.phone.editText?.doAfterTextChanged {
            formRegistro.phone.apply {
                value = it.toString()
                isValid = if (it.toString().isEmpty()) {
                    binding.phone.error = "No se permite el campo vacio"
                    false
                } else if(it.toString().length != 10) {
                    binding.phone.error = "La longitud debe ser de 10"
                    false
                } else {
                    binding.phone.error = null
                    true
                }
            }

            validateBtnEnabled()
        }

        binding.name.editText?.doAfterTextChanged {
            formRegistro.name.apply {
                value = it.toString()
                isValid = if (it.toString().isEmpty()) {
                    binding.name.error = "No se permite el campo vacio"
                    false
                } else {
                    binding.name.error = null
                    true
                }
            }



            validateBtnEnabled()
        }

        binding.lastName.editText?.doAfterTextChanged {
            formRegistro.lastName.apply {
                value = it.toString()
                isValid = if (it.toString().isEmpty()) {
                    binding.lastName.error = "No se permite el campo vacio"
                    false
                } else {
                    binding.lastName.error = null
                    true
                }
            }

            validateBtnEnabled()
        }

        binding.continuar.setOnClickListener {
            startActivity(
                Intent(
                this,
                    RegistroTwoActivity::class.java
                ).putExtras(
                    Bundle().apply {
                        putString("username", formRegistro.user.value)
                        putString("name", formRegistro.name.value)
                        putString("last_name", formRegistro.lastName.value)
                        putString("phone", formRegistro.phone.value)
                    }
                )
            )
        }
    }

    private fun validateBtnEnabled(){
        val isEnabled = formRegistro.user.isValid &&
                formRegistro.name.isValid &&
                formRegistro.lastName.isValid &&
                formRegistro.phone.isValid

        binding.continuar.isEnabled = isEnabled
    }

    private fun observables(){
        viewModel.errorObservale().observe(this){
            showToast(it)
        }

        viewModel.isLoading().observe(this){
            dialogLoader?.isLoading(it)
        }

        viewModel.responseObservable().observe(this){ response ->
            response?.let {
                showToast("Logueado correctamente ${it.name} ${it.last_name}")
            } ?: run { showToast("Ocurrio un error inesperado") }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

data class ItemForm(
    var value: String = "",
    var isValid: Boolean = false
)

