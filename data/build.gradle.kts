import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    compileSdkVersion(32)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(32)
        version = 1
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KLAYTN_HEADER_AUTHORIZATION", properties["klaytn_header_authorization"].toString())
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))

    implementation(libs.androidx.core)
    implementation(libs.junit.junit)
    implementation(libs.androidx.test.junit)
    
    // GsonConverter & Retrofit2 & Okhttp3 For Network Connection
    implementation(libs.bundles.gson)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.interceptor)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.kotlin.coroutine.core)

    //Room
    kapt(libs.androidx.room.compiler)
    implementation(libs.bundles.room)

    // moshi
    implementation(libs.bundles.moshi)

    // Preferences Data Store
    implementation(libs.bundles.datastore)
}