package com.owasp.top.mobile.demo.data

import com.owasp.top.mobile.demo.utils.HelperCipherApp

data class ResponseBase<T>(
    val statusCode: Int,
    val error: String?,
    val message: String?,
    var data: T? /// Puede estar cifrada o no
)

/// Para elementos que si estan cifrados
data class ObjectCryptiiBase(
    val data: String,
    val pass: String
)

class RequestBaseCrypt<T>(
    val data: String,
    val key: String,
){
    companion object {
        fun <T> set(data: T, cipher: HelperCipherApp): RequestBaseCrypt<T> {
            val dataCryptii = cipher.encryptAESData(data)
            return RequestBaseCrypt(
                data = dataCryptii.data,
                key = dataCryptii.pass
            )
        }
    }
}

