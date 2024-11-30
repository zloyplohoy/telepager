plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.android.library.compose)
}

android {
    namespace = "ag.sokolov.telepager.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
}
