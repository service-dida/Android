package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.example.convention.project.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidPresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("android.hilt")
                apply("androidx.navigation.safeargs.kotlin")
                apply("kotlin-parcelize")
                apply("com.google.gms.google-services")
            }

            extensions.configure<ApplicationExtension>{
                defaultConfig {
                    applicationId = "com.dida.android"
                    versionCode = 1
                    versionName = "1.0"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
                buildFeatures.dataBinding = true

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies{
                    add("implementation",project(":domain"))
                    add("implementation",project(":data"))
                }
            }
        }
    }
}
