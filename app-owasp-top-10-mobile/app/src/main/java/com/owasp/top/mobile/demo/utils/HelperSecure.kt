package com.owasp.top.mobile.demo.utils

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.owasp.top.mobile.demo.R
import javax.inject.Inject

class HelperSecure @Inject constructor() {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    fun fetchAndActivate(displayWelcomeApp: () -> Unit){
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                Logger.show("REMOTE_CONFIG_RESULT", "${task.isSuccessful} :: ${task.result}")
                displayWelcomeApp()
            }
    }


    val urlBaseBackend: String by lazy {
        remoteConfig.getString("url_api_backend")
    }

    val apiKeyX: String by lazy {
        remoteConfig.getString("api_key_x")
    }
}