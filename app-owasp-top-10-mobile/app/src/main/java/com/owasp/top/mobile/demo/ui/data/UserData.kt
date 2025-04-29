package com.owasp.top.mobile.demo.ui.data

data class InjectData<T>(
    var value: T? = null
)

data class UserData(
    val id: Int,
    val name: String?,
    val last_name: String?,
    val url_profile: String?,
    val status: String?,
    val token: String?
)