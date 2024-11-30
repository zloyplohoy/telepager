plugins {
    alias(libs.plugins.telepager.android.feature)
    alias(libs.plugins.telepager.android.library.compose)
}

android {
    namespace = "ag.sokolov.telepager.feature.test"
}

dependencies{
    implementation(projects.core.model)
    implementation(projects.core.telegram)
}
