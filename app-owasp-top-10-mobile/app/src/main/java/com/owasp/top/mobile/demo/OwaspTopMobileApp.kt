package com.owasp.top.mobile.demo

import android.app.Application
import android.content.pm.PackageManager
import com.owasp.top.mobile.demo.utils.AppSignatureHelper
import com.owasp.top.mobile.demo.utils.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OwaspTopMobileApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val list = AppSignatureHelper(this).appSignatures
        for (item in list){
            Logger.show("SIGNATURE:", message = item)
            //Weko1WLuPxe
            //Weko1WLuPxe
        }
    }
}