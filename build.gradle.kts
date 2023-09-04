buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
@kotlin.Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version ("7.1.2") apply false
    id("com.android.library") version ("7.1.2") apply false
    id("org.jetbrains.kotlin.android") version ("1.8.10") apply false
    id("org.jetbrains.kotlin.jvm") version ("1.8.10") apply false
    id("dagger.hilt.android.plugin") version ("2.44") apply false
    id("androidx.navigation.safeargs.kotlin") version ("2.4.2") apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
