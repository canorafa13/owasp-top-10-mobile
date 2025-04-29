package com.owasp.top.mobile.demo.data

sealed class Activities {
    class Request(
        val task_id: Int,
        val description: String
    ): Activities()
}