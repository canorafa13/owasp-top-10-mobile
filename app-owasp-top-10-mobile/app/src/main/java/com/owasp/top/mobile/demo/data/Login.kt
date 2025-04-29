package com.owasp.top.mobile.demo.data

import com.owasp.top.mobile.demo.ui.data.UserData


sealed class Login {
    data class Request(
        val username: String,
        val password: String
    ) : Login()

    data class Response(
        val id: Int,
        val name: String?,
        val last_name: String?,
        val url_profile: String?,
        val status: String?,
        val token: String?
    ) : Login() {
        fun toUserData(): UserData = UserData(
            id, name, last_name, url_profile, status, token
        )
    }
}
