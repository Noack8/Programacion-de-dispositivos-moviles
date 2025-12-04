// build.gradle.kts (en la raíz del proyecto)
// Este archivo configura los plugins globales que se usan en todo el proyecto.

buildscript {
    repositories {
        // En Kotlin DSL (.kts), cada declaración de repositorio va en su propia línea.
        // Esto es para que Gradle encuentre sus propias herramientas (plugins).
        google()
        mavenCentral()
    }
    dependencies {
        // Define la versión del plugin de Kotlin que Gradle usará.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // ¡ATENCIÓN! Cambiamos esta línea para forzar la versión del plugin de Compose.
    //id("org.jetbrains.kotlin.plugin.compose") version "1.9.24" apply false
}