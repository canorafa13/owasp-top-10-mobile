package com.owasp.top.mobile.demo.di

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.owasp.top.mobile.demo.BuildConfig
import com.owasp.top.mobile.demo.apis.OwaspInsecureApi
import com.owasp.top.mobile.demo.apis.OwaspSecureApi
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.utils.HelperSecure
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager



@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun getUnsafeOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        try {
            // Crea un trust manager que no valida los certificados del servidor
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory


            return builder
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideOwaspInsecureApi(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API_BACKEND)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OwaspInsecureApi::class.java)

    @Provides
    @Singleton
    fun provideOwaspSecureApi(helperSecure: HelperSecure, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(helperSecure.urlBaseBackend)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OwaspSecureApi::class.java)

    @Provides
    @Singleton
    fun provideStorage(
        @ApplicationContext context: Context,
        helperSecure: HelperSecure
    ): SharedPreferences {
        if(FlavorApp.isSecure()){
            val spec = KeyGenParameterSpec.Builder(
                "_androidx_security_master_key_",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()

            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                context.packageName + "_shared_data_enc",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            return context.getSharedPreferences(context.packageName + "_shared_data", Context.MODE_PRIVATE)
        }
    }


}