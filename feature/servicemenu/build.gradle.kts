plugins {
    alias(libs.plugins.telepager.android.feature)
    alias(libs.plugins.telepager.android.library.compose)
}

android {
    namespace = "ag.sokolov.telepager.feature.servicemenu"
}

dependencies{
    implementation(projects.core.concurrency)
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.designsystem)
    implementation(projects.core.telegram)
}
