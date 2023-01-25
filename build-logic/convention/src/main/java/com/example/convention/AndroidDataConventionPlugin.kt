package com.example.convention

import com.android.build.gradle.LibraryExtension
import com.example.convention.project.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("android.library")
                apply("android.hilt")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
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
