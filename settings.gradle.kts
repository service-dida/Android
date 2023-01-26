pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if( requested.id.id == "dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
            }
            if( requested.id.id == "androidx.navigation.safeargs.kotlin") {
                useModule("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
            }
        }
    }
}
buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
        gradlePluginPortal()
    }
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath("com.google.gms:google-services:4.3.13")
    }
}
// version catalogs
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/didalibs.versions.toml"))
        }
    }
}
rootProject.name = "DIDA"
include(":presentation")
include(":data")
include(":domain")
include(":model")
include(":common")
include(":feature")
include(":feature:login")
include(":feature:nickname")
include(":feature:home")
include(":feature:swap")
include(":feature:mypage")
include(":feature:add")
include(":feature:community")
include(":feature:create-community")
include(":feature:create-community-input")
