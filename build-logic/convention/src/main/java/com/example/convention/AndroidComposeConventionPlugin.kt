package com.example.convention

import com.android.build.gradle.LibraryExtension
import com.example.convention.project.configureCommonAndroid
import com.example.convention.project.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureComposeAndroid(extension)
            configureCommonAndroid(extension)
        }
    }
}
