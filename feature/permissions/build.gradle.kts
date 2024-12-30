plugins {
    alias(libs.plugins.telepager.android.feature)
    alias(libs.plugins.telepager.android.library.compose)
}

android {
    namespace = "ag.sokolov.telepager.feature.permissions"
}

dependencies{
    implementation(projects.core.designsystem)

    implementation(libs.accompanist.permissions)
}
