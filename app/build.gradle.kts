plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.movienow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.movienow"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.2")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha18")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Core Android & UI
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0") // For "pull to refresh"

    // --- Architecture Components (MVVM) ---
    // ViewModel: Manages UI-related data in a lifecycle-conscious way
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // LiveData: Observable data holder class
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    // Fragment KTX: Provides Kotlin extensions for fragments
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    // Activity KTX: Provides Kotlin extensions for activities
    implementation("androidx.activity:activity-ktx:1.8.2")


    // --- Networking (Retrofit) ---
    // Retrofit: A type-safe HTTP client for Android and Java
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter: To parse JSON into Kotlin objects
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp Logging Interceptor: To log network requests and responses (very useful for debugging)
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


    // --- Image Loading (Glide) ---
    // Glide: An efficient image loading and caching library
    implementation("com.github.bumptech.glide:glide:4.16.0")


    // --- Coroutines for Asynchronous Programming ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}