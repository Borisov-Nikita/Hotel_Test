@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "nik.borisov.hoteltest"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "nik.borisov.hoteltest"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.android.appCompat)
    implementation(libs.android.core)
    implementation(libs.android.constraintLayout)
    implementation(libs.android.navigationFragments)

    implementation(libs.google.material)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
    kapt(libs.kotlin.metadata)

    implementation(project(mapOf("path" to ":core:common")))
    implementation(project(mapOf("path" to ":core:presentation")))
    implementation(project(mapOf("path" to ":core:theme")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":features:booking")))
    implementation(project(mapOf("path" to ":features:hotel")))
    implementation(project(mapOf("path" to ":features:paid")))
    implementation(project(mapOf("path" to ":features:rooms")))
}