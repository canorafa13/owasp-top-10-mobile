package com.owasp.top.mobile.demo.apis

import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.data.ResponseBase
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface OwaspSecureApi {
    @POST("owasp-demo/api/v1/signon")
    suspend fun login(
        @Header("api-key-x") apiKeyX: String,
        request: Login.Request
    ): Response<ResponseBase<Login.Response>>
}