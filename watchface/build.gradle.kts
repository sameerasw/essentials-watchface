import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.application")
}

android {
    namespace = "com.sameerasw.essentials.watchface"
    compileSdk = 35

    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.sameerasw.essentials.watchface"
        minSdk = 34
        targetSdk = 37
        versionCode = 10000000
        versionName = "1.0.0"

        manifestPlaceholders["publisher"] = "AndroidStudioKoala-2024.1.2"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            /** We are not using isDebuggable flag as it is not possible to debug Watch Face Format package.
             * Instead, we debug com.samsung.wear.watchface.runtime (Galaxy Watches) and/or
             * com.google.wear.watchface.runtime (Pixel Watches)
             */
            isDebuggable = false
        }
    }
}