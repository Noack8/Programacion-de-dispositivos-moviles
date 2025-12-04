// settings.gradle.kts
// Este archivo define la configuración global de Gradle para el proyecto.

pluginManagement {
    // Aquí es donde se le dice a Gradle dónde encontrar los plugins.
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Esencial para muchos plugins de Kotlin.
    }
}

dependencyResolutionManagement {
    // Aquí es donde se le dice a Gradle dónde encontrar las LIBRERÍAS (dependencias).
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Proyecto1"
include(":app")