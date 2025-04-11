package com.owasp.top.mobile.demo.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.owasp.top.mobile.demo.data.IOResult
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.data.ResponseBase
import com.owasp.top.mobile.demo.domain.repository.OwaspRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val owaspRepository: OwaspRepository
): ViewModel(){
    private val _response = MutableLiveData<Login.Response?>()

    fun responseObservable(): LiveData<Login.Response?> = _response

    private val _error = MutableLiveData<String>()

    fun errorObservale(): LiveData<String> = _error

    fun login(username: String, password: String, remember: Boolean = false){
        viewModelScope.launch {
            val result = owaspRepository.login(username, password)
            when(result) {
                is IOResult.Success -> {
                    if (remember) {
                        // Guardar en base de datos
                    } else {
                        // Borrar de la base de datos
                    }
                    _response.postValue(result.data?.data)
                }
                is IOResult.Error -> {
                    _error.postValue(result.message)
                }
                else -> Unit
            }
        }
    }
}