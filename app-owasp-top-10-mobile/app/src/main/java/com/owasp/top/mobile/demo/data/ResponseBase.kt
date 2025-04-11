package com.owasp.top.mobile.demo.data

data class ResponseBase<T>(
    val statusCode: Int,
    val error: String?,
    val message: String?,
    val data: T?
)
