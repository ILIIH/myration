plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        buildConfigField("String", "HUGGINGFACE_API_KEY", "\"${project.findProperty("HUGGINGFACE_API_KEY")}\"")
        testInstrumentationRunner = "com.example.data.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
        androidResources = false
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
        debug {
            matchingFallbacks += listOf("withAudioRecognition", "withoutAudioRecognition")
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

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    implementation("androidx.health.connect:connect-client:1.1.0-alpha11")

    // Room components
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(project(":core"))
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Room extensions
    implementation("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-paging:2.6.1")

    implementation(project(":domain"))

    // DI
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")

    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson for JSON parsing
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3") // Optional: Logging

    // Paging
    implementation ("androidx.paging:paging-runtime:3.3.6")

    // Local unit tests
    testImplementation ("androidx.test:core:1.4.0")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    testImplementation ("com.google.truth:truth:1.1.3")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.1")
    testImplementation ("io.mockk:mockk:1.10.5")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.1.0-alpha04")

    // Instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.37")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.37")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    androidTestImplementation("io.mockk:mockk-android:1.10.5")
    androidTestImplementation("androidx.test:runner:1.4.0")

}
