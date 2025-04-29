package com.owasp.top.mobile.demo.domain.repository

import com.owasp.top.mobile.demo.apis.OwaspInsecureApi
import com.owasp.top.mobile.demo.apis.OwaspSecureApi
import com.owasp.top.mobile.demo.data.IOResult
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.data.RequestBaseCrypt
import com.owasp.top.mobile.demo.data.SignUp
import com.owasp.top.mobile.demo.datasource.StorageApp
import com.owasp.top.mobile.demo.domain.base.BaseRespository
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.ui.data.UserData
import com.owasp.top.mobile.demo.utils.HelperCipherApp
import com.owasp.top.mobile.demo.utils.HelperSecure
import com.owasp.top.mobile.demo.utils.Logger
import javax.inject.Inject

class OwaspRepository @Inject constructor(
    private val helperSecure: HelperSecure,
    private val owaspSecureApi: OwaspSecureApi,
    private val owaspInsecureApi: OwaspInsecureApi,
    private val storageApp: StorageApp,
    private val cipher: HelperCipherApp
) : BaseRespository() {

    suspend fun login(username: String, password: String): IOResult<Login.Response> {
        try {
            val request = Login.Request(username, password)
            Logger.show("LOGIN", request.toString())
            val response: Login.Response = if (FlavorApp.isSecure()) {
                secure(cipher) { owaspSecureApi.login(helperSecure.apiKeyX, cipher.encryptAESData(request)) }
            } else {
                insecure { owaspInsecureApi.login(request) }
            }
            Logger.show("LOGIN", response.toString())
            return IOResult.Success(response)
        }catch (e: Exception) {
            e.printStackTrace()
            return IOResult.Error(e.message.toString())
        }
    }

    suspend fun signUp(username: String, password: String, name: String, last_name: String, phone: String): IOResult<SignUp.Response> {
        try {
            val request = SignUp.Request(username, password, name, last_name, phone)

            val response: Int = if(FlavorApp.isSecure()) {
                secure(cipher) { owaspSecureApi.signUp(helperSecure.apiKeyX, cipher.encryptAESData(request)) }
            } else {
                insecure { owaspInsecureApi.signUp(request) }
            }

            return IOResult.Success(SignUp.Response(response))

        }catch (e: Exception) {
            e.printStackTrace()
            return IOResult.Error(e.message.toString())
        }
    }

    fun saveCredentials(username: String, password: String): IOResult<Boolean> {
        storageApp.password = password
        storageApp.username = username
        return IOResult.Success(true)
    }

    fun getCredentialsSaved(): IOResult<Pair<String, String>?> {
        var result: Pair<String, String>? = null
        if (storageApp.isSaved){
            result = Pair(storageApp.username, storageApp.password)
        }
        return IOResult.Success(result)
    }

    fun saveUserData(userData: UserData): IOResult<Boolean> {
        storageApp.setUserData(userData)
        return IOResult.Success(true)
    }

    fun getUserData() = storageApp.getUserData()
}