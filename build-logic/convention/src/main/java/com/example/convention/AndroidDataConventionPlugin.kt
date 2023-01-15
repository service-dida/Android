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
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies{
                add("implementation",project(":domain"))
            }

        }
    }
}
