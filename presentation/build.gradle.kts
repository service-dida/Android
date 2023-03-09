plugins {
    id("android.presentation")
}

android {
    signingConfigs {
        named("debug") {
            storeFile = file("../keystore/debug.keystore")
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storePassword = "android"
        }
        register("release") {
            storeFile = file("../keystore/release.keystore")
            storePassword = "projectdida"
            keyAlias = "dida"
            keyPassword = "projectdida"
        }
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
        named("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    // Firebase Push
    implementation(platform(libs.firebase.bom))
}
