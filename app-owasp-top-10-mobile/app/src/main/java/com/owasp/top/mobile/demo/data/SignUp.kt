package com.owasp.top.mobile.demo.data

sealed class SignUp {
    data class Request(
        val username: String,
        val password: String,
        val name: String,
        val last_name: String,
        val phone: String
    ): SignUp()

    data class Response(
        val result: Int
    ): SignUp()
}