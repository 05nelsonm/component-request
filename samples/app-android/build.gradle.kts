import io.matthewnelson.components.dependencies.Deps
import io.matthewnelson.components.dependencies.KaptDeps
import io.matthewnelson.components.dependencies.Versions

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dependencies")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }

    defaultConfig {
        applicationId("io.matthewnelson.sampleandroid")
        minSdkVersion(Versions.minSdk23)
        targetSdkVersion(Versions.compileSdk)
        versionCode(1)
        versionName("1.0.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        testInstrumentationRunnerArguments["disableAnalytics"] = "true"
    }

    // Gradle 4.0's introduction of Google analytics to Android App Developers.
    // https://developer.android.com/studio/releases/gradle-plugin
    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":samples:activity"))

    implementation(Deps.google.hilt)
    kapt(KaptDeps.google.hilt.hilt)
}
