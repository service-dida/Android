import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("android.presentation")
}

dependencies {
    // Firebase Push
    implementation(platform(libs.firebase.bom))
}
