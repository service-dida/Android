package com.example.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.findKaptConfiguration

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
                apply("kotlin-kapt")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", (libs.findDependency("hilt-android").get()))
                add("kapt", (libs.findDependency("hilt-android-compiler").get()))
            }
        }
    }

}
