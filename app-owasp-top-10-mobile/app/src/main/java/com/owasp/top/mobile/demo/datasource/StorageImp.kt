package com.owasp.top.mobile.demo.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class StorageImp @Inject constructor(
    val sharedPreferences: SharedPreferences
) {

    inline fun <reified T> set(key: String, value: T) {
        when (T::class) {
            Int::class -> sharedPreferences.edit()?.putInt(key, value as Int)?.apply()
            String::class -> sharedPreferences.edit()?.putString(key, value as String)?.apply()
            Boolean::class -> sharedPreferences.edit()?.putBoolean(key, value as Boolean)?.apply()
            Float::class -> sharedPreferences.edit()?.putFloat(key, value as Float)?.apply()
            Long::class -> sharedPreferences.edit()?.putLong(key, value as Long)?.apply()
            else -> throw Exception("The value cannot be saved: '$value'")
        }
    }

    inline fun <reified T> get(key: String): T {
        return when (T::class) {
            Int::class -> sharedPreferences.getInt(key, -1) as T
            String::class -> sharedPreferences.getString(key, "") as T
            Boolean::class -> sharedPreferences.getBoolean(key, false) as T
            Float::class -> sharedPreferences.getFloat(key, -1F) as T
            Long::class -> sharedPreferences.getLong(key, -1L) as T
            else -> throw Exception("Could not get value with key '$key'")
        }
    }

    fun remove(key: String) {
        sharedPreferences.edit()?.remove(key)?.apply()
    }
}
