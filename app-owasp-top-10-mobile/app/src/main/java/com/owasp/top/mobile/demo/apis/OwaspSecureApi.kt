package com.owasp.top.mobile.demo.apis

import com.owasp.top.mobile.demo.data.ObjectCryptiiBase
import com.owasp.top.mobile.demo.data.ResponseBase
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OwaspSecureApi {
    @POST("secure/owasp-demo/api/v1/signIn")
    suspend fun login(
        @Header("api-key-x") apiKeyX: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>
}