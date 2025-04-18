package com.owasp.top.mobile.demo.domain.repository

import com.owasp.top.mobile.demo.apis.OwaspInsecureApi
import com.owasp.top.mobile.demo.apis.OwaspSecureApi
import com.owasp.top.mobile.demo.data.IOResult
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.data.ResponseBase
import com.owasp.top.mobile.demo.datasource.StorageApp
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.utils.HelperSecure
import javax.inject.Inject

class OwaspRepository @Inject constructor(
    private val helperSecure: HelperSecure,
    private val owaspSecureApi: OwaspSecureApi,
    private val owaspInsecureApi: OwaspInsecureApi,
    private val storageApp: StorageApp
) {
    suspend fun login(username: String, password: String): IOResult<ResponseBase<Login.Response>?> {
        val request = Login.Request(username, password)
        val response = if (FlavorApp.isSecure()){
            owaspSecureApi.login(helperSecure.apiKeyX, request)
        } else {
            owaspInsecureApi.login(request)
        }

        if (response.isSuccessful){
            return IOResult.Success(response.body())
        }

        return IOResult.Error(response.message())
    }

    suspend fun saveCredentials(username: String, password: String): IOResult<Boolean> {
        storageApp.password = password
        storageApp.username = username
        return IOResult.Success(true)
    }

    suspend fun getCredentialsSaved(): IOResult<Pair<String, String>?> {
        var result: Pair<String, String>? = null
        if (storageApp.isSaved){
            result = Pair(storageApp.username, storageApp.password)
        }
        return IOResult.Success(result)
    }
}