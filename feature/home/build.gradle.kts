plugins {
    alias(libs.plugins.telepager.android.feature)
    alias(libs.plugins.telepager.android.library.compose)
}

android {
    namespace = "ag.sokolov.telepager.feature.home"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)

    implementation(libs.accompanist.permissions)
    implementation(libs.qrkit)
}
