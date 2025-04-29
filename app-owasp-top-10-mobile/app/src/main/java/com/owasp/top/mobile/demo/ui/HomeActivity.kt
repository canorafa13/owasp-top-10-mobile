package com.owasp.top.mobile.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.owasp.top.mobile.demo.databinding.ActivityHomeBinding
import com.owasp.top.mobile.demo.domain.HomeViewModel
import com.owasp.top.mobile.demo.ui.adapters.OptionsHomeAdapter
import com.owasp.top.mobile.demo.ui.adapters.OptionsItemHome
import com.owasp.top.mobile.demo.ui.common.AlertDialogApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapterOptions: OptionsHomeAdapter
    private var listOptions: MutableList<OptionsItemHome> = mutableListOf()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //populateList()

        adapterOptions = OptionsHomeAdapter(listOptions)

        binding.list.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            setHasFixedSize(true)
            adapter = adapterOptions
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        homeViewModel.provideUserData()?.let {
            binding.fullName.text = "${it.name} ${it.last_name}"
        }
    }

    private fun populateList(){
        listOptions.apply {
            addAll(
                listOf(
                    OptionsItemHome("M1: Uso inadecuado de credenciales - Inseguro"){
                        AlertDialogApp(this@HomeActivity)
                            .apply {
                                setTitle("Inseguro")
                                setMessage("Revisar las credenciales a nivel codigo insertadas hardcode")
                                setPositiveButton("De acuerdo"){

                                }

                            }.show()
                    },
                    OptionsItemHome("M1: Uso inadecuado de credenciales - Seguro"){
                        AlertDialogApp(this@HomeActivity)
                            .apply {
                                setTitle("Seguro")
                                setMessage("Revisar las credenciales que no existen a nivel codigo")
                                setPositiveButton("De acuerdo"){

                                }

                            }.show()
                    },
                )
            )
        }
    }
}