package com.owasp.top.mobile.demo.data

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

