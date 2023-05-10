package com.example.convention.project

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.*

internal fun Project.configureComposeAndroid(
    commonExtensions: CommonExtension<*, *, *, *>
) {
    commonExtensions.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.0-alpha01"
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            // Compose
            add("implementation", libs.findBundle("compose").get())
            add("implementation", libs.findBundle("compose-navigation").get())
            add("implementation", libs.findLibrary("io-coil-compose").get())

            add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-manifest").get())
        }
    }
}
