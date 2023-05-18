package com.example.convention.project

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureCommonAndroid(
    commonExtensions: CommonExtension<*, *, *, *>
) {
    commonExtensions.apply {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation", libs.findLibrary("junit-junit").get())
            add("implementation", libs.findLibrary("androidx-test-junit").get())
            add("implementation", libs.findLibrary("androidx-test-espresso").get())
        }
    }
}
