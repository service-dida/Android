buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
@kotlin.Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version("7.1.2") apply false
    id("com.android.library") version("7.1.2") apply false
    id("org.jetbrains.kotlin.android") version("1.6.10") apply false
    id("org.jetbrains.kotlin.jvm") version("1.6.10") apply false
    id("dagger.hilt.android.plugin") version("2.44") apply false
    id("androidx.navigation.safeargs.kotlin") version("2.3.5") apply false
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
