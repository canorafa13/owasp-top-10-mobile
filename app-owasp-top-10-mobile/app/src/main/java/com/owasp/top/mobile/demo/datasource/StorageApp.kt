package com.owasp.top.mobile.demo.datasource

import com.google.gson.Gson
import com.owasp.top.mobile.demo.ui.data.UserData
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

    fun setUserData(userData: UserData) = storageImp.set("user_data", Gson().toJson(userData))

    fun getUserData(): UserData? {
        return try {
            val json = storageImp.get<String>("user_data")
            Gson().fromJson(json, UserData::class.java)
        }catch (e: Exception){
            null
        }
    }

    val isSaved: Boolean
        get() = username.isNotEmpty() && password.isNotEmpty()
}