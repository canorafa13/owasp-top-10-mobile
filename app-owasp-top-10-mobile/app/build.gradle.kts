plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.services)

}

android {
    namespace = "com.owasp.top.mobile.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.owasp.top.mobile.demo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appLabel"] = "@string/app_name"
    }

    signingConfigs {
        create("release") {
            storeFile = file("owasp-mobile.jks")
            storePassword = System.getenv("STORE_PWD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PWD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding {
            enable = true
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("insecure"){
            manifestPlaceholders["usesCleartextTraffic"] = "true"
            manifestPlaceholders["appLabel"] = "OWASP Insecure"
            dimension = "version"
            applicationIdSuffix = ".insecure"
            versionNameSuffix = "-insecure"
            buildConfigField("String","API_KEY_X", "\"Alza_safd9209jfw893293823\"")
            buildConfigField("String", "URL_API_BACKEND", "\"http://172.20.10.2:80/\"")
        }
        create("secure"){
            manifestPlaceholders["usesCleartextTraffic"] = "false"
            manifestPlaceholders["appLabel"] = "OWASP Secure"

            dimension = "version"
            applicationIdSuffix = ".secure"
            versionNameSuffix = "-secure"
            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String","API_KEY_X", "\"\"")
            buildConfigField("String", "URL_API_BACKEND", "\"https://server.domain.com/\"")


        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.dagger.hilt.android)
    implementation(libs.firebase.config)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.security.crypto.ktx)
    implementation(project(":interceptor-logger"))
    kapt(libs.dagger.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}