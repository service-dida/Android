plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies{
    compileOnly(libs.android.pluginGradle)
    compileOnly(libs.kotlin.pluginGradle)
}

gradlePlugin{
    plugins{
        register("AndroidHilt"){
            id = "android.hilt"
            implementationClass = "com.example.convention.AndroidHiltConventionPlugin"
        }
        register("AndroidLibrary"){
            id = "android.library"
            implementationClass = "com.example.convention.AndroidLibraryConventionPlugin"
        }
    }
}
