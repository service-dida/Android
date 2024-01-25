package com.example.convention

import com.android.build.gradle.LibraryExtension
import com.example.convention.project.configureCommonAndroid
import com.example.convention.project.configureComposeAndroid
import com.example.convention.project.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("android.library")
                apply("android.hilt")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("androidx.navigation.safeargs.kotlin")
            }

            extensions.configure<LibraryExtension> {
                buildFeatures.dataBinding = true
                configureCommonAndroid(this)
            }


            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":data"))
                add("implementation", project(":domain"))
                add("implementation", project(":common"))

                // Android Common
                add("implementation", libs.findLibrary("androidx-core").get())
                add("implementation", libs.findLibrary("androidx-appcompat").get())
                add("implementation", libs.findLibrary("android-material").get())

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

                // Paging
                add("implementation", libs.findLibrary("androidx-paging").get())

                // Swipe Refresh Layout
                add("implementation", libs.findLibrary("androidx-swipe-refresh-layout").get())
            }
        }
    }
}
