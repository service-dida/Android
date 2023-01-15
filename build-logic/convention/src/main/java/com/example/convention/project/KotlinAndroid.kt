package com.example.convention.project

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.*

internal fun Project.configureKotlinAndroid(
    commonExtensions : CommonExtension<*,*,*,*>
){
    commonExtensions.apply {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        compileSdk = 33

        defaultConfig{
            minSdk = 21
            buildConfigField("String", "KLAYTN_HEADER_AUTHORIZATION", properties["klaytn_header_authorization"].toString())
            /* Hide Key (Must In Local.Properties)*/
            buildConfigField("String", "KAKAO_NATIVE_APP_KEY", properties["kakao_native_app_key"].toString())
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY_FOR_MANIFEST"] = properties.getProperty("kakao_native_app_key_for_manifest")
        }

        compileOptions{
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions{
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
    }
}

fun CommonExtension<*,*,*,*>.kotlinOptions(block : KotlinJvmOptions.() -> Unit){
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
