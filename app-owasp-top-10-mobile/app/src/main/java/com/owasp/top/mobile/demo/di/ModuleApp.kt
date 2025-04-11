package com.owasp.top.mobile.demo.di

import com.owasp.top.mobile.demo.BuildConfig
import com.owasp.top.mobile.demo.apis.OwaspInsecureApi
import com.owasp.top.mobile.demo.apis.OwaspSecureApi
import com.owasp.top.mobile.demo.utils.HelperSecure
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ModuleApp {
    @Provides
    fun provideOwaspInsecureApi() =
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API_BACKEND)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OwaspInsecureApi::class.java)

    fun provideOwaspSecureApi(helperSecure: HelperSecure) =
        Retrofit.Builder()
            .baseUrl(helperSecure.urlBaseBackend)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OwaspSecureApi::class.java)

}