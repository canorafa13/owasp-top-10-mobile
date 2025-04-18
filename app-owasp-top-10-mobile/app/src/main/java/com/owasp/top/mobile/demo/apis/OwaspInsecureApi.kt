package com.owasp.top.mobile.demo.apis

import com.owasp.top.mobile.demo.BuildConfig
import com.owasp.top.mobile.demo.data.Login
import com.owasp.top.mobile.demo.data.ResponseBase
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OwaspInsecureApi {
    @POST("owasp-demo/api/v1/signon")
    @Headers("api-key-x: ${BuildConfig.API_KEY_X}")
    suspend fun login(@Body request: Login.Request): Response<ResponseBase<Login.Response>>
}