plugins {
    alias(libs.plugins.telepager.android.application)
    alias(libs.plugins.telepager.android.application.compose)
    alias(libs.plugins.telepager.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ag.sokolov.telepager"

    defaultConfig {
        applicationId = "ag.sokolov.telepager"
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
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(projects.feature.bot)
    implementation(projects.feature.home)
    implementation(projects.feature.permissions)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
