package com.owasp.top.mobile.demo.environment

import com.owasp.top.mobile.demo.BuildConfig

object FlavorApp {
    fun isInsecure(): Boolean = BuildConfig.FLAVOR == "insecure"

    fun isSecure(): Boolean = BuildConfig.FLAVOR == "secure"
}