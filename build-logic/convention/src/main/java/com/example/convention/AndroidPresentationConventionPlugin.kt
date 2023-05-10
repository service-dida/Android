package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.example.convention.project.configureComposeAndroid
import com.example.convention.project.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.util.*

class AndroidPresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("android.hilt")
                apply("androidx.navigation.safeargs.kotlin")
                apply("kotlin-parcelize")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            }

            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureComposeAndroid(this)

                defaultConfig {
                    applicationId = "com.dida.android"
                    versionCode = 1
                    versionName = "1.0"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    targetSdk = 33
                    minSdk = 24
                    buildConfigField("String", "KLAYTN_HEADER_AUTHORIZATION", properties["KLAYTN_HEADER_AUTHORIZATION"].toString())
                    /* Hide Key (Must In Local.Properties)*/
                    buildConfigField("String", "KAKAO_NATIVE_APP_KEY", properties["kakao_native_app_key"].toString())
                }

                buildFeatures {
                    dataBinding = true
                    viewBinding = true
                }

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    add("implementation", project(":domain"))
                    add("implementation", project(":data"))
                    add("implementation", project(":common"))

                    add("implementation", project(":feature:splash"))
                    add("implementation", project(":feature:nft-detail"))
                    add("implementation", project(":feature:nft-sale"))
                    add("implementation", project(":feature:email"))
                    add("implementation", project(":feature:login"))
                    add("implementation", project(":feature:nickname"))
                    add("implementation", project(":feature:home"))
                    add("implementation", project(":feature:swap"))
                    add("implementation", project(":feature:swap-history"))
                    add("implementation", project(":feature:add"))
                    add("implementation", project(":feature:community"))
                    add("implementation", project(":feature:community-detail"))
                    add("implementation", project(":feature:create-community"))
                    add("implementation", project(":feature:create-community-input"))
                    add("implementation", project(":feature:mypage"))
                    add("implementation", project(":feature:wallet"))
                    add("implementation", project(":feature:update-profile"))
                    add("implementation", project(":feature:password"))
                    add("implementation", project(":feature:settings"))
                    add("implementation", project(":feature:recent-nft"))
                    add("implementation", project(":feature:hot-user"))
                    add("implementation", project(":feature:hot-seller"))
                    add("implementation", project(":feature:buy-nft"))
                    add("implementation", project(":feature:compose"))
                    add("implementation", project(":feature:user-profile"))
                    add("implementation", project(":feature:temp-password"))
                    add("implementation", project(":feature:change-password"))

                    // Android Common
                    add("implementation", libs.findLibrary("androidx-core").get())
                    add("implementation", libs.findLibrary("androidx-appcompat").get())
                    add("implementation", libs.findLibrary("android-material").get())
                    add("implementation", libs.findLibrary("junit-junit").get())
                    add("implementation", libs.findLibrary("androidx-test-junit").get())
                    add("implementation", libs.findLibrary("androidx-test-espresso").get())

                    // Android Ui 관련
                    add("implementation", libs.findLibrary("androidx-constraintlayout").get())
                    add("implementation", libs.findLibrary("androidx-recyclerview").get())
                    add("implementation", libs.findLibrary("circle-imageview").get())
                    add("implementation", libs.findLibrary("facebook-shimmer").get())
                    add("implementation", libs.findLibrary("android-lottie").get())

                    // Network
                    add("implementation", libs.findBundle("gson").get())
                    add("implementation", libs.findLibrary("squareup-retrofit2").get())
                    add("implementation", libs.findLibrary("squareup-okhttp").get())
                    add("implementation", libs.findLibrary("squareup-okhttp-interceptor").get())

                    // Glide
                    add("implementation", libs.findLibrary("glide-glide").get())
                    add("implementation", libs.findLibrary("glide-compiler").get())

                    // Navigation
                    add("implementation", libs.findBundle("androidx.navigation").get())
                    add("implementation", libs.findLibrary("androidx.navigation.test").get())

                    // Coroutine Scope
                    add("implementation", libs.findBundle("lifecycle").get())
                    add("implementation", libs.findBundle("kotlinx-coroutine").get())

                    // Room
                    add("kapt", libs.findLibrary("androidx-room-compiler").get())
                    add("implementation", libs.findBundle("room").get())

                    // Kakao SDK
                    add("implementation", libs.findLibrary("kakao-sdk").get())

                    // Fragment Result API
                    add("implementation", libs.findLibrary("androidx-fragment-request").get())

                    // Data Store
                    add("implementation", libs.findBundle("datastore").get())

                    // Firebase
                    add("implementation", libs.findLibrary("firebase-analytics").get())
                    add("implementation", libs.findLibrary("firebase-messaging").get())
                    add("implementation", libs.findLibrary("firebase-service").get())
                    add("implementation", libs.findLibrary("firebase-crashlytics").get())

                    // Paging
                    add("implementation", libs.findLibrary("androidx-paging").get())
                }
            }
        }
    }
}
