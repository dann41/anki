pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "anki"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    ":shared",
    ":core-api",
    ":core",
    ":auth",
    ":api",
    ":cmd"
)