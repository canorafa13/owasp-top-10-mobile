package com.owasp.top.mobile.demo.datasource

import javax.inject.Inject

class StorageApp @Inject constructor(
    private val storageImp: StorageImp
) {

    var username: String
        get() = storageImp.get("username")
        set(value) = storageImp.set("username", value)

    var password: String
        get() = storageImp.get("password")
        set(value) = storageImp.set("password", value)


    val isSaved: Boolean
        get() = username.isNotEmpty() && password.isNotEmpty()
}