package com.owasp.top.mobile.demo.utils

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
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
        remoteConfig.addOnConfigUpdateListener(object: ConfigUpdateListener{
            override fun onUpdate(p0: ConfigUpdate) {
                Logger.show("REMOTE_CONFIG_RESULT_LIVE", "Success :: ${p0.updatedKeys}")
                remoteConfig.activate()
            }

            override fun onError(p0: FirebaseRemoteConfigException) {
                Logger.show("REMOTE_CONFIG_RESULT_LIVE", "error :: ${p0.message}")
            }
        })
    }


    val urlBaseBackend: String by lazy {
        remoteConfig.getString("url_api_backend")
    }

    val apiKeyX: String by lazy {
        remoteConfig.getString("api_key_x")
    }

    val spKeyStoreAlias: String by lazy {
        remoteConfig.getString("sp_keystore_alias")
    }

    val spKeySize: Int by lazy {
        remoteConfig.getLong("sp_key_size").toInt()
    }

    val rsaPrivateKey: String by lazy {
        remoteConfig.getString("rsa_private_key")
    }

    val rsaPublicKey: String by lazy {
        remoteConfig.getString("rsa_public_key")
    }
}