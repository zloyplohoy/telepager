plugins {
    alias(libs.plugins.telepager.android.feature)
    alias(libs.plugins.telepager.android.library.compose)
}

android {
    namespace = "ag.sokolov.telepager.feature.bot"
}

dependencies{
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
}
