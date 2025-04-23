package com.owasp.top.mobile.demo.domain.repository

import com.owasp.top.mobile.demo.apis.OwaspInsecureApi
import com.owasp.top.mobile.demo.apis.OwaspSecureApi
import com.owasp.top.mobile.demo.data.IOResult
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.datasource.StorageApp
import com.owasp.top.mobile.demo.domain.base.BaseRespository
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.utils.HelperCipherApp
import com.owasp.top.mobile.demo.utils.HelperCipherRSA
import com.owasp.top.mobile.demo.utils.HelperSecure
import javax.inject.Inject

class OwaspRepository @Inject constructor(
    private val helperSecure: HelperSecure,
    private val owaspSecureApi: OwaspSecureApi,
    private val owaspInsecureApi: OwaspInsecureApi,
    private val storageApp: StorageApp,
    private val helperCipherApp: HelperCipherApp
) : BaseRespository() {

    suspend fun login(username: String, password: String): IOResult<Login.Response> {
        try {
            val request = Login.Request(username, password)

            val response: Login.Response = if (FlavorApp.isSecure()) {
                secure(helperCipherApp) { owaspSecureApi.login(helperSecure.apiKeyX, helperCipherApp.encryptAESData(request)) }
            } else {
                insecure { owaspInsecureApi.login(request) }
            }
            return IOResult.Success(response)
        }catch (e: Exception) {
            e.printStackTrace()
            return IOResult.Error(e.message.toString())
        }
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