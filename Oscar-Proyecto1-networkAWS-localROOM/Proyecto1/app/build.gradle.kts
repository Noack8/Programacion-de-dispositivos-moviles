plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Este es para Room, está bien.
}

android {
    namespace = "com.example.proyecto1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyecto1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // ¡LA CLAVE! Aquí es donde se usa la versión del compilador de Compose.
        // Apunta a la nueva variable 'composeCompilerExtension' de libs.versions.toml.
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerExtension.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

// --- Core & Compose ---
    implementation(platform(libs.androidx.compose.bom)) // El BOM asegura versiones compatibles.
    implementation(platform(libs.androidx.compose.bom)) {
        exclude(group = "androidx.compose.material", module = "material")
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)


    // --- Navegación ---
    implementation(libs.androidx.navigation.compose)

    // --- ViewModel ---
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // --- Corrutinas ---
    implementation(libs.kotlinx.coroutines.android)

    // --- Red (Retrofit & OkHttp) ---
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor) // Para ver logs de la API en Logcat.


    // --- Base de Datos Local (Room) ---
    implementation(libs.room.runtime) // Núcleo de Room.
    implementation(libs.room.ktx)     // Soporte para Corrutinas y Flow.
    kapt(libs.room.compiler)          // ¡LA CLAVE! El procesador de anotaciones.

    // --- Preferencias (DataStore) ---
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.sqlite) // Dependencia de bajo nivel para SQLite en Android.

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // También para testing, el BOM
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}