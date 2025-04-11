package com.owasp.top.mobile.demo.utils

import android.util.Log
import com.owasp.top.mobile.demo.BuildConfig

object Logger {
    fun show(tag: String = "${BuildConfig.APPLICATION_ID} :: Logger", message: String = ""){
        Log.e(tag, message)
    }
}