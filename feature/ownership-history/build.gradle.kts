plugins {
    id("android.feature")
    id("android.compose")
}

dependencies {
    implementation(project(":feature:compose"))
}
