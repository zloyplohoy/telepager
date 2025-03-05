plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.koin)
}

android {
    namespace = "ag.sokolov.telepager.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.telegram)
}
