package com.owasp.top.mobile.demo.domain.base

import com.google.gson.Gson
import com.owasp.top.mobile.demo.data.ObjectCryptiiBase
import com.owasp.top.mobile.demo.data.ResponseBase
import com.owasp.top.mobile.demo.utils.ApiErrorException
import com.owasp.top.mobile.demo.utils.HelperCipherApp
import com.owasp.top.mobile.demo.utils.ObjectNullException

open class BaseRespository {

    suspend fun <Response> BaseRespository.insecure(api: suspend () -> retrofit2.Response<ResponseBase<Response>>): Response {
        val result = api.invoke()
        if (result.isSuccessful){
            return result.body()?.data ?: throw ObjectNullException()
        }
        val error = Gson().fromJson(result.errorBody()?.string(), ResponseBase::class.java)
        throw ApiErrorException(error.message ?: result.message())
    }

    inline fun <reified Response> BaseRespository.secure(cipher: HelperCipherApp, api: () -> retrofit2.Response<ResponseBase<ObjectCryptiiBase>>): Response {
        val result = api.invoke()
        if (result.isSuccessful) {
            val body = result.body()?.data ?: throw  ObjectNullException()
            val objectResult: Response = cipher.decryptAESData(body)
            return objectResult

        }
        val error = Gson().fromJson(result.errorBody()?.string(), ResponseBase::class.java)
        throw ApiErrorException(error.message ?: result.message())
    }
}