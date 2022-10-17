import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")

    id("com.google.gms.google-services")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    compileSdkVersion(32)

    defaultConfig {
        applicationId = "com.dida.android"
        minSdkVersion(21)
        targetSdkVersion(32)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        /* Hide Key (Must In Local.Properties)*/
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", properties["kakao_native_app_key"].toString())
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY_FOR_MANIFEST"] = properties.getProperty("kakao_native_app_key_for_manifest")
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

    buildFeatures.dataBinding = true
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // GsonConverter & Retrofit2 & Okhttp3 For Network Connection
    implementation(libs.bundles.gson)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.interceptor)

    // Glide Library For Image
    implementation(libs.glide.glide)
    annotationProcessor(libs.glide.compiler)

    //Navigation
    implementation(libs.bundles.androidx.navigation)
    androidTestImplementation(libs.androidx.navigation.test)

    // https://github.com/ybq/Android-SpinKit
    implementation(libs.android.spinkit)

    // life cycle scope
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.bundles.kotlinx.coroutine)

    //Room
    kapt(libs.androidx.room.compiler)
    implementation(libs.bundles.room)

    // 카카오 로그인
    implementation(libs.kakao.sdk)

    //Circle ImageView
    implementation(libs.circle.imageview)

    // skeleton Ui
    implementation(libs.facebook.shimmer)

    // Fragment Result API
    implementation(libs.androidx.fragment.request)

    // Preferences Data Store
    implementation(libs.bundles.datastore)

    // Firebase Push
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
}