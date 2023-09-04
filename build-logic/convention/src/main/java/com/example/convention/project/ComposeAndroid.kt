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
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtensions.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {
            // Compose
            val bom = libs.findLibrary("androidxComposeBom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findLibrary("androidxComposeMaterial").get())
            add("implementation", libs.findLibrary("androidxComposeMaterial3").get())
            add("implementation", libs.findLibrary("androidComposeMaterial3WindowSizeClass").get())
            add("implementation", libs.findLibrary("androidxComposeRuntimeTracing").get())
            add("implementation", libs.findLibrary("androidxActivityCompose").get())
            add("implementation", libs.findLibrary("androidxConstraintLayoutCompose").get())
            add("implementation", libs.findLibrary("androidxLifecycleLivedataKtx").get())
            add("implementation", libs.findLibrary("androidxLifecycleRuntimeCompose").get())
            add("implementation", libs.findLibrary("androidxLifecycleViewModelCompose").get())
            add("implementation", libs.findLibrary("androidxLifecycleViewModelCompose").get())
            add("implementation", libs.findLibrary("androidxLifecycleViewModelCompose").get())
            add("implementation", libs.findLibrary("androidx-compose-pager").get())
            add("implementation", libs.findLibrary("androidx-compose-pager-indicator").get())
            add("implementation", libs.findLibrary("coilCompose").get())
            add("implementation", libs.findLibrary("coilGifCompose").get())
        }
    }
}
