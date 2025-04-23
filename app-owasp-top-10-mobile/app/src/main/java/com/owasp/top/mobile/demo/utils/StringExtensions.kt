package com.owasp.top.mobile.demo.utils

import com.google.gson.Gson


inline fun <reified T> String.toObject(): T {
    return Gson().fromJson(this, T::class.java)
}

fun String.toHex(): String {
    val stringByteArray: ByteArray = this.toByteArray()
    return stringByteArray.joinToString("") { byte ->
        String.format("%02x", byte)
    }
}

fun Int.getRandomStringBySize(): String{
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..this)
        .map { allowedChars.random() }
        .joinToString("")
}

fun String.getRandom(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}