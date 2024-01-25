dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/didalibs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
