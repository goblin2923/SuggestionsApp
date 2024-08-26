plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

//    id("com.google.devtools.ksp") version "2.0.10-1.0.24"
    id("com.google.devtools.ksp")
//    id("org.jetbrains.kotlin.plugin.serialization")

//    kotlin("jvm") version "2.0.0" // or kotlin("multiplatform") or any other kotlin plugin
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.suggestionsapp_v2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.suggestionsapp_v2"
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
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}", "/META-INF/INDEX.LIST")
        }
    }
}

dependencies {
//    ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
//    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.logback.classic)

//    room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.ui.text.google.fonts)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

//    Test Dispatcher
    implementation (libs.kotlinx.coroutines.test)
//    viewModel()
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
//    view model scope
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}