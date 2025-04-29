package com.owasp.top.mobile.demo.data

sealed class Tasks {
    class Request(
        username: String,
        title: String,
        description: String
    ): Tasks()
}