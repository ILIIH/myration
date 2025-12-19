plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "../build-const.gradle.kts")
android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters += listOf("arm64-v8a")
        }
    }
    flavorDimensions += listOf("version")
    productFlavors {
        create("withAudioRecognition") {
            dimension = "version"
        }
        create("withoutAudioRecognition") {
            dimension = "version"
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
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${project.extra["kotlin_version"]}")
    implementation("androidx.appcompat:appcompat:${project.extra["appcompat_version"]}")
    implementation("com.google.android.material:material:${project.extra["material_version"]}")

    // DI
    implementation("com.google.dagger:hilt-android:${project.extra["dagger_version"]}")
    kapt("com.google.dagger:hilt-compiler:${project.extra["dagger_version"]}")

    // Text recognition AI
    implementation ("com.google.mlkit:text-recognition:${project.extra["mlkit_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${project.extra["coroutines_play_services_version"]}")

    // Text to model AI libs
    implementation("com.microsoft.onnxruntime:onnxruntime-android:1.15.1")
    implementation ("ai.djl.huggingface:tokenizers:0.23.0")

    // Voice recognition AI
    "withAudioRecognitionImplementation"(project(":lib"))

    implementation(project(":domain"))
    testImplementation(kotlin("test"))

}
