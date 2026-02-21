apply(from = "../build-const.gradle.kts")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.myration"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myration"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("withAudioRecognition") {
            dimension = "version"
            applicationIdSuffix = ".withaudio"
            versionNameSuffix = "-withaudio"
        }
        create("withoutAudioRecognition") {
            dimension = "version"
            applicationIdSuffix = ".withoutaudio"
            versionNameSuffix = "-withoutaudio"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${project.extra["kotlin_version"]}")
    implementation("com.google.android.material:material:${project.extra["material_version"]}")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Compose
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.8")
    implementation("androidx.wear.compose:compose-material:1.4.1")
    implementation("androidx.exifinterface:exifinterface:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.work:work-runtime-ktx:2.9.1")
    implementation("androidx.hilt:hilt-common:1.3.0")
    implementation("androidx.compose.foundation:foundation:1.10.1")
    implementation("androidx.compose.foundation:foundation-layout:1.10.2")
    implementation("androidx.compose.foundation:foundation:1.10.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.4")
    debugImplementation("androidx.compose.ui:ui-tooling-preview:1.7.4")

    implementation("androidx.health.connect:connect-client:1.1.0-alpha11")

    // DI
    implementation("com.google.dagger:hilt-android:${project.extra["dagger_version"]}")
    kapt("com.google.dagger:hilt-compiler:${project.extra["dagger_version"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${project.extra["hilt_navigation_version"]}")

    // Image download
    implementation("io.coil-kt:coil-compose:${project.extra["koil_version"]}")

    // CameraX
    implementation("androidx.camera:camera-camera2:1.4.2")
    implementation("androidx.camera:camera-lifecycle:1.4.2")
    implementation("androidx.camera:camera-view:1.4.2")
    implementation("androidx.camera:camera-extensions:1.4.2")

    // Paging
    implementation("androidx.paging:paging-runtime:3.3.6")
    implementation("androidx.paging:paging-compose:3.3.6")

    // Optimization
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")

    // Add Crashlytics and Analytics
    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":core-ui"))
}
