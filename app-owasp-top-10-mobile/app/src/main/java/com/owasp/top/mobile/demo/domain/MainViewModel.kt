package com.owasp.top.mobile.demo.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.owasp.top.mobile.demo.data.IOResult
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.domain.repository.OwaspRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val owaspRepository: OwaspRepository
): ViewModel(){

    private val _isLoading = MutableLiveData(false)

    fun isLoading(): LiveData<Boolean> = _isLoading

    private val _response = MutableLiveData<Login.Response?>()

    fun responseObservable(): LiveData<Login.Response?> = _response

    private val _username = MutableLiveData<String?>()

    fun usernameObservable(): LiveData<String?> = _username


    private val _password = MutableLiveData<String?>()

    fun passwordObservable(): LiveData<String?> = _password

    private val _error = MutableLiveData<String>()

    fun errorObservale(): LiveData<String> = _error

    private val _isSuccessSignUp = MutableLiveData(false)

    fun isSuccessSignUp(): LiveData<Boolean> = _isSuccessSignUp

    fun login(username: String, password: String, remember: Boolean = false){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = owaspRepository.login(username, password)
            when(result) {
                is IOResult.Success -> {
                    if (remember) {
                        owaspRepository.saveCredentials(username, password)
                    } else {
                        owaspRepository.saveCredentials("", "")
                    }
                    _response.postValue(result.data)
                }
                is IOResult.Error -> {
                    _error.postValue(result.message)
                }
                else -> Unit
            }
            _isLoading.postValue(false)
        }
    }

    fun signUp(username: String, password: String, name: String, last_name: String, phone: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = owaspRepository.signUp(username, password, name, last_name, phone)
            when(result) {
                is IOResult.Success -> {
                    _isSuccessSignUp.postValue(true)
                }
                is IOResult.Error -> {
                    _error.postValue(result.message)
                }
                else -> Unit
            }

            _isLoading.postValue(false)
        }
    }

    fun getCredentials(){
        viewModelScope.launch {
            val result = owaspRepository.getCredentialsSaved() as IOResult.Success

            result.data?.let {
                _username.postValue(it.first)
                _password.postValue(it.second)
            }

        }
    }
}