package com.owasp.top.mobile.demo.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.owasp.top.mobile.demo.data.IOResult
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.domain.repository.OwaspRepository
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.ui.data.InjectData
import com.owasp.top.mobile.demo.ui.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val owaspRepository: OwaspRepository,
    private val userData: InjectData<UserData>
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
                    if (FlavorApp.isSecure()){
                        userData.value = result.data.toUserData()
                    } else {
                        owaspRepository.saveUserData(result.data.toUserData())
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
            when(val result = owaspRepository.signUp(username, password, name, last_name, phone)) {
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

    fun getUserData() = owaspRepository.getUserData()

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