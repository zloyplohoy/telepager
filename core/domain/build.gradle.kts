plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.hilt)
}

android {
    namespace = "ag.sokolov.telepager.core.domain"
}

dependencies {
    api(projects.core.data)
    implementation(projects.core.concurrency)
}
