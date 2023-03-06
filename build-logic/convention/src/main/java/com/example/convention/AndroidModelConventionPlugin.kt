package com.example.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidModelConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("androidx-core").get())
                add("implementation", libs.findLibrary("junit-junit").get())
                add("implementation", libs.findLibrary("androidx-test-junit").get())

                // Gson
                add("implementation", libs.findBundle("gson").get())
            }
        }
    }
}
