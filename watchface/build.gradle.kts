import java.io.File
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files

plugins {
    id("com.android.application")
}

android {
    namespace = "com.sameerasw.essentials.watchface"
    compileSdk = 35

    buildFeatures {
        buildConfig = false
    }

    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.sameerasw.essentials.watchface"
        minSdk = 34
        targetSdk = 37
        versionCode = 10000002
        versionName = "1.0.0"

        manifestPlaceholders["publisher"] = "AndroidStudioKoala-2024.1.2"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = false
        }
    }
}

tasks.matching { it.name.startsWith("build") && it.name.endsWith("PreBundle") }.configureEach {
    doLast {
        val outputFiles = outputs.files.asFileTree.files
        for (file in outputFiles) {
            if (file.name == "base.zip" && file.exists()) {
                val zipUri = URI.create("jar:" + file.toURI().toString())
                val env = mapOf("create" to "false")
                try {
                    FileSystems.newFileSystem(zipUri, env).use { fs ->
                        val dexDir = fs.getPath("dex")
                        if (Files.exists(dexDir)) {
                            Files.walk(dexDir)
                                .sorted(Comparator.reverseOrder())
                                .forEach { Files.deleteIfExists(it) }
                        }
                    }
                } catch (e: Exception) {
                    project.logger.warn("Failed to clean dex directory from ${file}: ${e.message}")
                }
            }
        }
    }
}
