package com.owasp.top.mobile.demo.data

sealed class IOResult <T>{
    data object Loading: IOResult<Nothing>()
    data class Success<T>(val data: T): IOResult<T>()
    data class Error<T>(val message: String): IOResult<T>()
}