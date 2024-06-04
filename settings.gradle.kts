pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {

            library("android.coreKtx", "androidx.core:core-ktx:1.9.0")
            library("android.appCompat", "androidx.appcompat:appcompat:1.6.1")
            library("android.constraintLayout", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("android.fragmentKtx", "androidx.fragment:fragment-ktx:1.5.5")
            library("android.activityKtx", "androidx.activity:activity-ktx:1.6.1")
            library("android.lifecycleRuntimeKtx", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
            library("android.lifecycleViewModelKtx", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
            library("android.paging", "androidx.paging:paging-runtime-ktx:3.1.0")

            library("google.material", "com.google.android.material:material:1.12.0")

            library("test.junit", "junit:junit:4.13.2")
        }
    }
}

rootProject.name = "WeatherForecast"
include(":app")
