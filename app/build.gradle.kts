plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

fun getLocalPropertyKey(key: String): String? {
    return com.android.build.gradle.internal.cxx.configure
        .gradleLocalProperties(rootDir).getProperty(key) ?: ""
}

android {
    namespace = "com.example.weatherforecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherforecast"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val weather_api_key = getLocalPropertyKey("weather_api_key")

        buildConfigField("String", "WEATHER_API_KEY", "\"$weather_api_key\"")
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
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.android.coreKtx)
    implementation(libs.android.appCompat)
    implementation(libs.android.constraintLayout)
    implementation(libs.android.fragmentKtx)
    implementation(libs.android.activityKtx)
    implementation(libs.android.lifecycleRuntimeKtx)
    implementation(libs.android.lifecycleViewModelKtx)
    implementation(libs.android.paging)

    implementation(libs.backend.okHttp)
    implementation(libs.backend.okHttpInterceptor)
    implementation(libs.backend.retrofit)
    implementation(libs.backend.moshi)

    implementation(libs.google.dagger)
    implementation(libs.google.material)

    kapt(libs.google.daggerAnnotationProcessor)

    testImplementation(libs.test.junit)
}