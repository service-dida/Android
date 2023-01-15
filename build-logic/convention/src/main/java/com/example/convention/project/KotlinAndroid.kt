package com.example.convention.project

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtensions : CommonExtension<*,*,*,*>
){
    commonExtensions.apply {
        compileSdk = 33

        defaultConfig{
            minSdk = 21
        }

        compileOptions{
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions{
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

fun CommonExtension<*,*,*,*>.kotlinOptions(block : KotlinJvmOptions.() -> Unit){
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
