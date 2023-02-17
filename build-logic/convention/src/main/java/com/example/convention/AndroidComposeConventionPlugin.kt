package com.example.convention

import com.android.build.gradle.LibraryExtension
import com.example.convention.project.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply("android.library")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
                buildFeatures.compose = true

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    // Compose
                    add("implementation", libs.findBundle("compose").get())
                    add("implementation", libs.findLibrary("compose-paging").get())
                }
            }
        }
    }
}
