package com.example.convention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.util.*

class AndroidDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("android.library")
                apply("android.hilt")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
            }

            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())

            extensions.configure<LibraryExtension> {
                compileSdk = 33
                defaultConfig {
                    minSdk = 24
                    buildConfigField("String", "KLAYTN_HEADER_AUTHORIZATION", properties["KLAYTN_HEADER_AUTHORIZATION"].toString())
                    /* Hide Key (Must In Local.Properties)*/
                    buildConfigField("String", "KAKAO_NATIVE_APP_KEY", properties["KAKAO_NATIVE_APP_KEY"].toString())
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":domain"))

                add("implementation", libs.findLibrary("androidx-core").get())
                add("implementation", libs.findLibrary("junit-junit").get())
                add("implementation", libs.findLibrary("androidx-test-junit").get())

                // Network
                add("implementation", libs.findBundle("gson").get())
                add("implementation", libs.findLibrary("squareup-retrofit2").get())
                add("implementation", libs.findLibrary("squareup-okhttp").get())
                add("implementation", libs.findLibrary("squareup-okhttp-interceptor").get())

                // Coroutine
                add("implementation", libs.findLibrary("kotlin-coroutine-core").get())

                // Room
                add("kapt", libs.findLibrary("androidx-room-compiler").get())
                add("implementation", libs.findBundle("room").get())

                // Moshi
                add("implementation", libs.findBundle("moshi").get())

                // DataStore
                add("implementation", libs.findBundle("datastore").get())
            }
        }
    }
}
